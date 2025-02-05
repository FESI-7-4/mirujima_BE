package com.todo.mirujima_be.todo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDetailResponse {

    @Schema(description = "목표 고유키", example = "1")
    private Long goalId;
    @Schema(description = "목표 제목", example = "목표 제목")
    private String goalTitle;

    @Schema(description = "투두 고유키", example = "1")
    private Long id;
    @Schema(description = "투두 제목", example = "투두 제목")
    private String title;
    @Schema(description = "파일 경로", example = "/home/image/sample.jpg")
    private String filePath;
    @Schema(description = "파일 경로")
    private String linkUrl;
    @Schema(description = "완료 여부", example = "true")
    private Boolean done;
    @Schema(description = "등록자", example = "홍길동")
    private String createdBy;
    @Schema(description = "등록일", example = "2024-10-11 15:21:00")
    private LocalDateTime createdAt;
    @Schema(description = "수정자", example = "홍길동")
    private String modifiedBy;
    @Schema(description = "수정일", example = "2024-10-11 15:21:00")
    private LocalDateTime modifiedAt;

    public TodoDetailResponse(Long id, Long goalId, String goalTitle, String title, String filePath, Boolean done,
                              String createdBy, LocalDateTime createdAt, String modifiedBy, LocalDateTime modifiedAt) {
        this.id = id;
        this.goalId = goalId;
        this.goalTitle = goalTitle;
        this.title = title;
        this.filePath = filePath;
        this.done = done;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
    }

}
