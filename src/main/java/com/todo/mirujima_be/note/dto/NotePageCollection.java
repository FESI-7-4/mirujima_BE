package com.todo.mirujima_be.note.dto;

import com.todo.mirujima_be.note.dto.response.NoteResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotePageCollection {

    private Long lastSeenId;
    private Integer remainingCount;
    private List<NoteResponse> notes;

}
