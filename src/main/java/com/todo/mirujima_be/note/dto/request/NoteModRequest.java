package com.todo.mirujima_be.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteModRequest {

    @NotBlank(message = "노트 제목은 비어있을 수 없습니다.")
    @Size(max = 30, message = "노트 제목은 30자를 초과하실 수 없습니다")
    @Schema(description = "노트 제목", example = "노트 제목")
    private String title;
    @NotBlank(message = "노트 내용은 비어있을 수 없습니다.")
    @Schema(description = "노트 내용", example = "노트 내용")
    private String content;
    @Schema(description = "노트 링크", example = "노트 링크")
    private String linkUrl;

}
