package com.todo.mirujima_be.goal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GoalModRequest {

    @Schema(description = "목표 제목", example = "목표 제목 1", nullable = true)
    String title;

}
