package com.todo.mirujima_be.note.dto;

import com.todo.mirujima_be.note.dto.response.NoteResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class NotePageCollection {

    private Long lastSeenId;
    private Integer totalCount;
    private List<NoteResponse> notes;

}
