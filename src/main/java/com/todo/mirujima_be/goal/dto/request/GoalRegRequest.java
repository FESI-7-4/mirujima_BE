package com.todo.mirujima_be.goal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class GoalRegRequest {

    @NotEmpty
    @Size(min = 1, max = 30)
    @Schema(description = "목표 제목", example = "목표 제목 1", nullable = false)
    String title;

}
