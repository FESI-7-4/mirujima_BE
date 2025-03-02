package com.todo.mirujima_be.todo.dto;

import com.todo.mirujima_be.todo.dto.response.TodoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoPageCollection {

    private Long lastSeenId;
    private Integer remainingCount;
    private List<TodoResponse> todos;

}
