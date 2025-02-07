package com.todo.mirujima_be.user.dto.response;

import com.todo.mirujima_be.user.entity.User;
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
public class UserResponse {
    @Schema(description = "사용자 고유키", example = "1")
    private Long id;
    @Schema(description = "사용자의 유저네임", example = "test")
    private String username;
    @Schema(description = "사용자의 이메일", example = "test@test.com")
    private String email;
    @Schema(description = "가입 일자", example = "2024-10-11 15:21:00")
    private LocalDateTime createdAt;
    @Schema(description = "수정 일자", example = "2024-10-11 15:21:00")
    private LocalDateTime updatedAt;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
