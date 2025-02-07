package com.todo.mirujima_be.goal.dto;

import com.todo.mirujima_be.goal.entity.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GoalDto {

    @Schema(description = "목표 고유키", example = "1")
    private Long id;
    @Schema(description = "목표 제목", example = "목표 제목 1")
    private String title;

    public static GoalDto from(Goal goal) {
        return GoalDto.builder()
                .id(goal.getId())
                .title(goal.getTitle())
                .build();
    }

}
