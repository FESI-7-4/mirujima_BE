package com.todo.mirujima_be.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteListRequest {

  @Schema(description = "목표 고유키", example = "1")
  private Long goalId;
  @Schema(description = "현재 페이지에서 가장 작은 노트 고유키", example = "9999", defaultValue = "9223372036854775807")
  private Long lastSeenId = Long.MAX_VALUE;
  @Min(1)
  @Max(100)
  @Schema(description = "페이지 크기", example = "5", defaultValue = "5")
  private Integer pageSize = 5;
  @Schema(description = "목표 보유 유무", example = "true")
  private Boolean hasGoal;

}
