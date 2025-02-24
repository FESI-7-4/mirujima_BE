package com.todo.mirujima_be.goal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GoalRegRequest {

  @NotEmpty
  @Size(min = 1, max = 30)
  @Schema(description = "목표 제목", example = "목표 제목 1", nullable = false)
  String title;

  @Schema(description = "완료일", example = "2021-10-01T00:00:00", nullable = true)
  private LocalDateTime completionDate;

}
