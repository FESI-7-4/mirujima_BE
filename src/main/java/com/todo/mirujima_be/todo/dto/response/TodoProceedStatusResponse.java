package com.todo.mirujima_be.todo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoProceedStatusResponse {

    @Schema(description = "할일 수", example = "1")
    private int todoCount = 0;
    @Schema(description = "할일 완료 수", example = "1")
    private long completionTodoCount = 0;

}
