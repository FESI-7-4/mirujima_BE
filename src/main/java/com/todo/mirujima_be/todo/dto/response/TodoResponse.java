package com.todo.mirujima_be.todo.dto.response;

import com.todo.mirujima_be.auth.util.AuthUtil;
import com.todo.mirujima_be.goal.dto.GoalDto;
import com.todo.mirujima_be.todo.entity.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {

    @Schema(description = "목표 정보")
    private GoalDto goal;

    @Schema(description = "노트 고유키", example = "1")
    private Long noteId;

    @Schema(description = "완료 여부", example = "true")
    private Boolean done;
    @Schema(description = "파일 경로")
    private String linkUrl;
    @Schema(description = "파일 경로", example = "/home/image/sample.jpg")
    private String filePath;
    @Schema(description = "할일 제목", example = "할일 제목")
    private String title;
    @Schema(description = "할일 고유키", example = "1")
    private Long id;
    @Schema(description = "사용자 고유키", example = "1")
    private Long userId;
    @Schema(description = "등록일", example = "2024-10-11 15:21:00")
    private LocalDateTime createdAt;
    @Schema(description = "수정일", example = "2024-10-11 15:21:00")
    private LocalDateTime updatedAt;

    public static TodoResponse of(Todo todo) {
        var goalDto = GoalDto.from(todo.getGoal());
        var note = todo.getNote();
        return TodoResponse.builder()
                .goal(goalDto)
                .noteId(note == null ? null : note.getId())
                .done(todo.getDone())
                .linkUrl(todo.getLinkUrl())
                .filePath(todo.getFilePath())
                .title(todo.getTitle())
                .id(todo.getId())
                .userId(AuthUtil.getUserInfo().getId())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }

}
