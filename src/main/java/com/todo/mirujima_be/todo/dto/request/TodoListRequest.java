package com.todo.mirujima_be.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private Long goalId = 0L;
    @Schema(description = "완료 유무", example = "true/false")
    private Boolean done;
    @Schema(description = "현재 페이지에서 가장 작은 투두 고유키", example = "1", defaultValue = "0")
    private Long lastSeenId = 0L;

}
