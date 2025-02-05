package com.todo.mirujima_be.note.service;

import com.todo.mirujima_be.note.dto.request.NoteListRequest;
import com.todo.mirujima_be.note.dto.request.NoteModRequest;
import com.todo.mirujima_be.note.dto.request.NoteRegRequest;
import com.todo.mirujima_be.note.dto.response.NoteDeletedResponse;
import com.todo.mirujima_be.note.dto.response.NoteDetailListResponse;
import com.todo.mirujima_be.note.dto.response.NoteDetailResponse;
import com.todo.mirujima_be.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteDetailListResponse getNoteList(NoteListRequest taskNoteListRequest) {
        return null;
    }

    public NoteDetailResponse getNoteDetail(long noteId) {
        return null;
    }

    @Transactional
    public Long registerNote(NoteRegRequest noteRegRequest) {
        return null;
    }

    @Transactional
    public Long modifyNote(NoteModRequest noteModRequest) {
        return null;
    }

    @Transactional
    public NoteDeletedResponse deleteNote(long noteId) {
        return null;
    }

}
