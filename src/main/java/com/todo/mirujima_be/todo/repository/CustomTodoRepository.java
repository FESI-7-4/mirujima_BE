package com.todo.mirujima_be.todo.repository;

import com.todo.mirujima_be.todo.dto.request.TodoListRequest;

import java.util.List;

public interface CustomTodoRepository {

    List<Long> getTodoIdList(TodoListRequest todoListRequest);

}
