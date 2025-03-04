package com.todo.mirujima_be.note.service;

import static com.todo.mirujima_be.auth.util.AuthUtil.checkAuthority;

import com.todo.mirujima_be.common.contant.MirujimaConstants;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.note.dto.NotePageCollection;
import com.todo.mirujima_be.note.dto.request.NoteListRequest;
import com.todo.mirujima_be.note.dto.request.NoteModRequest;
import com.todo.mirujima_be.note.dto.request.NoteRegRequest;
import com.todo.mirujima_be.note.dto.response.NoteResponse;
import com.todo.mirujima_be.note.entity.Note;
import com.todo.mirujima_be.note.repository.NoteRepository;
import com.todo.mirujima_be.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NoteService {

  private final NoteRepository noteRepository;
  private final TodoRepository todoRepository;

  public NotePageCollection getNoteList(String email, NoteListRequest noteListRequest) {
    var noteCount = noteRepository.getNoteCount(noteListRequest);
    var noteIds = noteRepository.getNoteIdList(noteListRequest);
    var notes = noteRepository.findAllByIdIn(noteIds);
    var lastSeenTodoId = notes.stream().mapToLong(Note::getId).min().orElse(0L);
    return NotePageCollection.builder()
        .lastSeenId(lastSeenTodoId)
        .remainingCount(noteCount.intValue() - notes.size())
//        .notes(notes.stream().map(NoteResponse::of).toList())
        .build();
  }

  public NoteResponse getNoteDetail(String email, long noteId) {
    var note = noteRepository.findById(noteId).orElseThrow(() -> new AlertException("노트가 존재하지 않습니다"));
    checkAuthority(email, MirujimaConstants.Note.Note, note);
    return NoteResponse.of(note);
  }

  @Transactional
  public NoteResponse createNote(String email, NoteRegRequest noteRegRequest) {
    var note = Note.from(noteRegRequest);
    var todo = todoRepository.findById(noteRegRequest.getTodoId())
        .orElseThrow(() -> new AlertException("할일이 존재하지 않습니다."));
    if (todo.getNote() != null) {
      throw new AlertException("이미 노트가 존재합니다.");
    }
    checkAuthority(email, MirujimaConstants.Todo.Todo, todo);
    note.setTodo(todo);
    noteRepository.save(note);
    return NoteResponse.of(note);
  }

  @Transactional
  public NoteResponse modifyNote(String email, long noteId, NoteModRequest noteModRequest) {
    var note = noteRepository.findById(noteId).orElseThrow(() -> new AlertException("노트가 존재하지 않습니다"));
    note.modifyTo(noteModRequest);
    return NoteResponse.of(note);
  }

  @Transactional
  public void deleteNote(String email, long noteId) {
    var note = noteRepository.findById(noteId).orElseThrow(() -> new AlertException("노트가 존재하지 않습니다"));
    checkAuthority(email, MirujimaConstants.Note.Note, note);
    var todo = note.getTodo();
    note.setTodo(null);
    if (todo != null) {
      todo.setNote(null);
    }
    noteRepository.deleteById(noteId);
  }

}
