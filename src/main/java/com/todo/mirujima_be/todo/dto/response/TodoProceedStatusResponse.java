package com.todo.mirujima_be.todo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoProceedStatusResponse {

    @Schema(description = "투두 수", example = "1")
    private int todoCount;
    @Schema(description = "투두 완료 수", example = "1")
    private int completionTodoCount;
}
