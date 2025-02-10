package com.todo.mirujima_be.goal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalModRequest {

    @Schema(description = "목표 제목", example = "목표 제목 1", nullable = true)
    private String title;
    @Schema(description = "완료일", example = "2021-10-01T00:00:00", nullable = true)
    private LocalDateTime completionDate;

}
