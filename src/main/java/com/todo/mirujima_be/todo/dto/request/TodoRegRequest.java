package com.todo.mirujima_be.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class TodoRegRequest {

    @NotNull(message = "목표 고유키는 필수 값입니다.")
    @Schema(description = "목표 고유키", example = "1")
    private Long goalId;
    @NotBlank(message = "할일 제목은 필수 값입니다.")
    @Schema(description = "할일 제목", example = "1")
    private String title;
    @Schema(description = "할일 파일 경로", example = "/home/image/sample.jpg", nullable = true)
    private String filePath;
    @Schema(description = "할일 링크 경로", nullable = true)
    private String linkUrl;
    @Max(4)
    @Min(1)
    @Schema(description = "중요도", example = "1", defaultValue = "1")
    private Integer priority = 1;

}
