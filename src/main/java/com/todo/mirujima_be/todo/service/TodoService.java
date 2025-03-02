package com.todo.mirujima_be.todo.service;

import com.todo.mirujima_be.common.contant.MirujimaConstants;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.goal.entity.Goal;
import com.todo.mirujima_be.goal.repository.GoalRepository;
import com.todo.mirujima_be.todo.dto.TodoPageCollection;
import com.todo.mirujima_be.todo.dto.request.TodoCompletionRequest;
import com.todo.mirujima_be.todo.dto.request.TodoListRequest;
import com.todo.mirujima_be.todo.dto.request.TodoModRequest;
import com.todo.mirujima_be.todo.dto.request.TodoRegRequest;
import com.todo.mirujima_be.todo.dto.response.TodoProceedStatusResponse;
import com.todo.mirujima_be.todo.dto.response.TodoResponse;
import com.todo.mirujima_be.todo.entity.Todo;
import com.todo.mirujima_be.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.todo.mirujima_be.auth.util.AuthUtil.checkAuthority;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final GoalRepository goalRepository;

    public TodoPageCollection getTodoList(TodoListRequest todoListRequest) {
        var todoCount = todoRepository.getTodoCount(todoListRequest);
        var todoIds = todoRepository.getTodoIdList(todoListRequest);
        var todos = todoRepository.findAllById(todoIds);
        var lastSeenTodoId = todos.stream().mapToLong(Todo::getId).min().orElse(0L);
        return TodoPageCollection.builder()
                .lastSeenId(lastSeenTodoId)
                .remainingCount(todoCount.intValue() - todos.size())
                .todos(todos.stream().map(TodoResponse::of).toList())
                .build();
    }

    public TodoResponse getTodoDetail(String email, long todoId) {
        var todo = todoRepository.findById(todoId).orElseThrow(() -> new AlertException("목표가 존재하지 않습니다."));
        checkAuthority(email, MirujimaConstants.Todo.Todo, todo);
        return TodoResponse.of(todo);
    }

    public TodoProceedStatusResponse getProgressStatus(String email) {
        var todos = todoRepository.findAllByCreatedByEmail(email);
        long completedCount = todos.stream().filter(Todo::getDone).count();
        return TodoProceedStatusResponse.builder()
                .todoCount(todos.size())
                .completionTodoCount(completedCount)
                .build();
    }

    @Transactional
    public TodoResponse createTodo(String email, TodoRegRequest todoRegRequest) {
        var goalId = todoRegRequest.getGoalId();
        Goal goal = null;
        if (goalId != null) {
            goal = goalRepository.findById(todoRegRequest.getGoalId())
                    .orElseThrow(() -> new AlertException("목표가 존재하지 않습니다."));
            checkAuthority(email, MirujimaConstants.Goal.Goal, goal);
        }
        var todo = Todo.builder()
                .goal(goal)
                .title(todoRegRequest.getTitle())
                .linkUrl(todoRegRequest.getLinkUrl())
                .orgFileName(todoRegRequest.getFileName())
                .filePath(todoRegRequest.getFilePath())
                .priority(todoRegRequest.getPriority())
                .done(false)
                .build();
        todoRepository.save(todo);
        return TodoResponse.of(todo);
    }

    @Transactional
    public TodoResponse modifyTodo(String email, Long id, TodoModRequest todoModRequest) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new AlertException("할일이 없습니다."));
        checkAuthority(email, MirujimaConstants.Todo.Todo, todo);
        todo.modifyTo(todoModRequest);
        var goalId = todoModRequest.getGoalId();
        if (goalId != null) {
            var goal = goalRepository.findById(goalId).orElseThrow(() -> new AlertException("목표가 존재하지 않습니다."));
            todo.setGoal(goal);
        }
        return TodoResponse.of(todo);
    }

    @Transactional
    public TodoResponse completeTodo(String email, Long id, TodoCompletionRequest todoCompletionRequest) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new AlertException("할일이 없습니다."));
        checkAuthority(email, MirujimaConstants.Todo.Todo, todo);
        todo.setDone(todoCompletionRequest.getDone());
        todo.setCompletionDate(LocalDateTime.now());
        return TodoResponse.of(todo);
    }

    @Transactional
    public void deleteTodo(String email, long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new AlertException("할일이 없습니다."));
        checkAuthority(email, MirujimaConstants.Todo.Todo, todo);
        todoRepository.delete(todo);
    }

}
