package com.todo.mirujima_be.goal.dto.response;

import com.todo.mirujima_be.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class GoalResponse {
    @Schema(description = "목표의 id", example = "1")
    private Long id;
    @Schema(description = "목표의 이름", example = "제주도 목표")
    private String name;
    @Schema(description = "목표의 시작 날짜", example = "2021-10-01")
    private LocalDate startDate;
    @Schema(description = "목표의 종료 날짜", example = "2021-10-05")
    private LocalDate endDate;
    @Schema(description = "목표를 생성한 사람", example = """
            {
            "id": 1,
            "username": "test",
            "email": "test@test.com"
            }
            """)
    private UserResponse lastCreatedUser;
    @Schema(description = "목표을 생성한 시간", example = "2021-10-01T00:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "목표를 마지막으로 변경한 사람", example = """
            {
                "id": 1,
                "username": "test",
                "email": "test@test.com"
            }
            """)
    private UserResponse lastUpdatedUser;
    @Schema(description = "목표를 마지막으로 업데이트한 시간", example = "2021-10-01T00:00:00")
    private LocalDateTime updatedAt;
}
