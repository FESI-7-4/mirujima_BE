package com.todo.mirujima_be.note.dto.response;

import com.todo.mirujima_be.goal.dto.GoalDto;
import com.todo.mirujima_be.note.entity.Note;
import com.todo.mirujima_be.todo.dto.TodoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {

    @Schema(description = "할일 정보")
    private TodoDto todoDto;
    @Schema(description = "노트 내용")
    private String content;
    @Schema(description = "파일 경로")
    private String linkUrl;
    @Schema(description = "수정일", example = "2024-10-11 15:21:00")
    private LocalDateTime updatedAt;
    @Schema(description = "등록일", example = "2024-10-11 15:21:00")
    private LocalDateTime createdAt;
    @Schema(description = "노트 제목", example = "노트 제목")
    private String title;
    @Schema(description = "할일 고유키", example = "1")
    private Long id;
    @Schema(description = "목표 정보")
    private GoalDto goalDto;
    @Schema(description = "사용자 고유키", example = "1")
    private Long userId;

    public static NoteResponse of(Note note) {
        var todo = note.getTodo();
        var goal = todo.getGoal();
        return NoteResponse.builder()
                .todoDto(TodoDto.from(todo))
                .content(note.getContent())
                .linkUrl(note.getLinkUrl())
                .updatedAt(note.getUpdatedAt())
                .createdAt(note.getCreatedAt())
                .title(note.getTitle())
                .id(todo.getId())
                .goalDto(GoalDto.from(goal))
                .userId(note.getCreatedBy().getId())
                .build();
    }

}
