package com.todo.mirujima_be.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoListRequest {

    @Schema(description = "목표 고유키", example = "1")
    private Long goalId;
    @NotNull(message = "완료 유무는 필수 값입니다.")
    @Schema(description = "완료 유무", example = "true/false", defaultValue = "false")
    private Boolean done = false;
    @Schema(description = "현재 페이지에서 가장 작은 투두 고유키", example = "9999", defaultValue = "9999")
    private Long lastSeenId = Long.MAX_VALUE;
    @Schema(description = "페이지 크기", example = "5", defaultValue = "5")
    private Integer pageSize = 5;

}
