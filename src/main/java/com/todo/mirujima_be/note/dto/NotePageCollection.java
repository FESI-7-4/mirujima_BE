package com.todo.mirujima_be.note.dto;

import com.todo.mirujima_be.note.dto.response.NoteResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NotePageCollection {

  private Long lastSeenId;
  private Integer remainingCount;
  private List<NoteResponse> notes;

}
