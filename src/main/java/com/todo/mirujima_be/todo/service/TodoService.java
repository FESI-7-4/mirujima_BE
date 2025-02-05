package com.todo.mirujima_be.todo.service;

import com.todo.mirujima_be.todo.dto.request.TodoListRequest;
import com.todo.mirujima_be.todo.dto.request.TodoModRequest;
import com.todo.mirujima_be.todo.dto.request.TodoRegRequest;
import com.todo.mirujima_be.todo.dto.response.TodoDetailResponse;
import com.todo.mirujima_be.todo.dto.response.TodoProceedStatusResponse;
import com.todo.mirujima_be.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TodoService {

    private final TodoRepository taskRepository;

    public List<TodoDetailResponse> getTodoList(TodoListRequest todoListRequest) {
        return List.of();
    }

    public TodoDetailResponse getTodoDetail(long todoId) {
        return null;
    }

    public TodoProceedStatusResponse getProgressStatus() {
        return null;
    }

    @Transactional
    public void completeTodo(Long todoId, boolean done) {
    }

    @Transactional
    public Long registerTodo(TodoRegRequest todoRegRequest) {
        return null;
    }

    @Transactional
    public Long modifyTodo(TodoModRequest todoModRequest) {
        return null;
    }

    @Transactional
    public Long deleteTodo(long taskId) {
        return taskId;
    }

}
