package com.todo.mirujima_be.goal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateGoalRequest {
    @Schema(description = "목표 이름", example = "목표1", nullable = true)
    String name;
    @Schema(description = "목표 시작 날짜", example = "2024-02-12", nullable = true)
    LocalDate startDate;
    @Schema(description = "목표 종료 날짜", example = "2024-02-20", nullable = true)
    LocalDate endDate;
}
