package com.todo.mirujima_be.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoModRequest {

    @NotNull(message = "목표 고유키는 필수 값입니다.")
    @Schema(description = "목표 고유키", example = "1")
    private Long goalId;
    @NotNull(message = "투두 고유키는 필수 값입니다.")
    @Schema(description = "투두 고유키", example = "1")
    private Long id;
    @NotBlank(message = "투두 제목은 필수 값입니다.")
    @Schema(description = "투두 제목", example = "1")
    private String title;
    @Schema(description = "투두 파일 경로", example = "/home/image/sample.jpg", nullable = true)
    private String filePath;

}
