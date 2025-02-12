package com.todo.mirujima_be.note.repository;

import com.todo.mirujima_be.note.dto.request.NoteListRequest;

import java.util.List;

public interface CustomNoteRepository {

    List<Long> getNoteIdList(NoteListRequest noteListRequest);

    Long getNoteCount(NoteListRequest noteListRequest);

}
