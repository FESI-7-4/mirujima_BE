package com.todo.mirujima_be.todo.service;

import com.todo.mirujima_be.auth.util.AuthUtil;
import com.todo.mirujima_be.common.contant.MirujimaConstants;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.goal.repository.GoalRepository;
import com.todo.mirujima_be.todo.dto.TodoPageCollection;
import com.todo.mirujima_be.todo.dto.request.TodoListRequest;
import com.todo.mirujima_be.todo.dto.request.TodoModRequest;
import com.todo.mirujima_be.todo.dto.request.TodoRegRequest;
import com.todo.mirujima_be.todo.dto.response.TodoProceedStatusResponse;
import com.todo.mirujima_be.todo.dto.response.TodoResponse;
import com.todo.mirujima_be.todo.entity.Todo;
import com.todo.mirujima_be.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final GoalRepository goalRepository;

    public TodoPageCollection getTodoList(TodoListRequest todoListRequest) {
        var pageSize = todoListRequest.getPageSize();
        var lastSeenId = todoListRequest.getLastSeenId();
        if (pageSize == null) pageSize = MirujimaConstants.Todo.PAGE_SIZE;
        var pageable = PageRequest.of(0, pageSize);
        var todos = todoRepository.findAllByGoalIdAndDoneAndIdLessThanOrderByIdDesc(
                todoListRequest.getGoalId(), todoListRequest.getDone(), lastSeenId, pageable
        );
        var lastSeenTodoId = todos.stream().mapToLong(Todo::getId).min().orElse(0L);
        return TodoPageCollection.builder()
                .lastSeenId(lastSeenTodoId)
                .totalCount(todos.size())
                .todos(todos.stream().map(TodoResponse::of).toList())
                .build();
    }

    public TodoResponse getTodoDetail(long todoId) {
        var todoOptional = todoRepository.findById(todoId);
        return todoOptional.map(TodoResponse::of).orElse(null);
    }

    public TodoProceedStatusResponse getProgressStatus() {
        var userId = AuthUtil.getUserInfo().getId();
        var todos = todoRepository.findAllByCreatedById(userId);
        long completedCount = todos.stream().filter(Todo::getDone).count();
        return TodoProceedStatusResponse.builder()
                .todoCount(todos.size())
                .completionTodoCount(completedCount)
                .build();
    }

    @Transactional
    public TodoResponse registerTodo(TodoRegRequest todoRegRequest) {
        var goal = goalRepository.findById(todoRegRequest.getGoalId())
                .orElseThrow(() -> new AlertException("목표가 존재하지 않습니다."));
        var todo = Todo.builder()
                .goal(goal)
                .title(todoRegRequest.getTitle())
                .linkUrl(todoRegRequest.getLinkUrl())
                .filePath(todoRegRequest.getFilePath())
                .priority(todoRegRequest.getPriority())
                .done(false)
                .build();
        todoRepository.save(todo);
        return TodoResponse.of(todo);
    }

    @Transactional
    public TodoResponse modifyTodo(Long id, TodoModRequest todoModRequest) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new AlertException("할일이 없습니다."));
        todo.modifyTo(todoModRequest);
        return TodoResponse.of(todo);
    }

    @Transactional
    public void deleteTodo(long todoId) {
        todoRepository.deleteById(todoId);
    }

}
