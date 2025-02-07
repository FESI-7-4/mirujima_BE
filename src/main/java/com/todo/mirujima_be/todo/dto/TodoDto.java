package com.todo.mirujima_be.todo.dto;

import com.todo.mirujima_be.todo.entity.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TodoDto {

    @Schema(description = "할일 고유키", example = "1")
    private Long id;
    @Schema(description = "할일 제목", example = "목표 제목 1")
    private String title;
    @Schema(description = "완료 유무", example = "true/false")
    private Boolean done;
    @Schema(description = "파일 경로")
    private String linkUrl;
    @Schema(description = "파일 경로", example = "/home/image/sample.jpg")
    private String filePath;

    public static TodoDto from(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .done(todo.getDone())
                .filePath(todo.getFilePath())
                .linkUrl(todo.getLinkUrl())
                .build();
    }

}
