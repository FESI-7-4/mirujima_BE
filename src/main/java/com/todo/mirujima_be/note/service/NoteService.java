package com.todo.mirujima_be.note.service;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NoteService {

    private final NoteRepository noteRepository;
    private final TodoRepository todoRepository;

    public NotePageCollection getNoteList(NoteListRequest noteListRequest) {
        var pageSize = noteListRequest.getPageSize();
        var lastSeenId = noteListRequest.getLastSeenId();
        if (pageSize == null) pageSize = MirujimaConstants.Note.PAGE_SIZE;
        var pageable = PageRequest.of(0, pageSize);
        var noteCount = noteRepository.countByTodoGoalId(noteListRequest.getGoalId());
        var notes = noteRepository.findAllByTodoGoalIdAndIdLessThanOrderByIdDesc(
                noteListRequest.getGoalId(), lastSeenId, pageable
        );
        var lastSeenTodoId = notes.stream().mapToLong(Note::getId).min().orElse(0L);
        return NotePageCollection.builder()
                .lastSeenId(lastSeenTodoId)
                .totalCount(notes.size())
                .notes(notes.stream().map(NoteResponse::of).toList())
                .build();
    }

    public NoteResponse getNoteDetail(long noteId) {
        var note = noteRepository.findById(noteId).orElseThrow(() -> new AlertException("노트가 존재하지 않습니다"));
        return NoteResponse.of(note);
    }

    @Transactional
    public NoteResponse registerNote(NoteRegRequest noteRegRequest) {
        var note = Note.from(noteRegRequest);
        var todo = todoRepository.findById(noteRegRequest.getTodoId())
                .orElseThrow(() -> new AlertException("할일이 존재하지 않습니다."));
        note.setTodo(todo);
        noteRepository.save(note);
        return NoteResponse.of(note);
    }

    @Transactional
    public NoteResponse modifyNote(long id, NoteModRequest noteModRequest) {
        var note = noteRepository.findById(id).orElseThrow(() -> new AlertException("노트가 존재하지 않습니다"));
        note.modifyTo(noteModRequest);
        return NoteResponse.of(note);
    }

    @Transactional
    public void deleteNote(long noteId) {
        noteRepository.deleteById(noteId);
    }

}
