package com.todo.mirujima_be.todo.dto;

import com.todo.mirujima_be.todo.dto.response.TodoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TodoPageCollection {

    private Long lastSeenId;
    private Integer totalCount;
    private List<TodoResponse> todos;

}
