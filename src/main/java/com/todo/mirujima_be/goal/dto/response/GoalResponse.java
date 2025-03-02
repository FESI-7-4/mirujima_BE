package com.todo.mirujima_be.goal.dto.response;

import com.todo.mirujima_be.goal.entity.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalResponse {

    @Schema(description = "목표의 고유키", example = "1")
    private Long id;
    @Schema(description = "유저의 고유키", example = "1")
    private Long userId;
    @Schema(description = "목표의 이름", example = "제주도 목표")
    private String title;
    @Schema(description = "완료일", example = "2021-10-01T00:00:00")
    private LocalDateTime completionDate;
    @Schema(description = "목표을 생성한 시간", example = "2021-10-01T00:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "목표를 마지막으로 업데이트한 시간", example = "2021-10-01T00:00:00")
    private LocalDateTime updatedAt;

    public static GoalResponse of(Goal goal) {
        return GoalResponse.builder()
                .id(goal.getId())
                .userId(goal.getCreatedBy().getId())
                .title(goal.getTitle())
                .completionDate(goal.getCompletionDate())
                .createdAt(goal.getCreatedAt())
                .updatedAt(goal.getUpdatedAt())
                .build();
    }

}
