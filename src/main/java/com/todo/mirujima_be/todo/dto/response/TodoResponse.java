package com.todo.mirujima_be.todo.dto.response;

import com.todo.mirujima_be.goal.dto.GoalDto;
import com.todo.mirujima_be.todo.entity.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @Schema(description = "파일 이름", example = "orgFileName.jpg")
  private String orgFileName;
  @Schema(description = "파일 경로", example = "/home/image/uuid.jpg")
  private String filePath;
  @Schema(description = "할일 제목", example = "할일 제목")
  private String title;
  @Schema(description = "할일 고유키", example = "1")
  private Long id;
  @Schema(description = "사용자 고유키", example = "1")
  private Long userId;
  @Schema(description = "완료일", example = "2021-10-01T00:00:00")
  private LocalDateTime completionDate;
  @Schema(description = "등록일", example = "2024-10-11 15:21:00")
  private LocalDateTime createdAt;
  @Schema(description = "수정일", example = "2024-10-11 15:21:00")
  private LocalDateTime updatedAt;

  @Schema(description = "중요도", example = "1")
  private Integer priority;

  public static TodoResponse of(Todo todo) {
    GoalDto goalDto = null;
    var goal = todo.getGoal();
    if (goal != null) {
      goalDto = GoalDto.from(goal);
    }
    var note = todo.getNote();
    return TodoResponse.builder()
        .goal(goalDto)
        .noteId(note == null ? null : note.getId())
        .done(todo.getDone())
        .linkUrl(todo.getLinkUrl())
        .orgFileName(todo.getOrgFileName())
        .filePath(todo.getFilePath())
        .title(todo.getTitle())
        .id(todo.getId())
        .userId(todo.getCreatedBy().getId())
        .priority(todo.getPriority())
        .completionDate(todo.getCompletionDate())
        .createdAt(todo.getCreatedAt())
        .updatedAt(todo.getUpdatedAt())
        .build();
  }

}
