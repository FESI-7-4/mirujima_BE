package com.todo.mirujima_be.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todo.mirujima_be.common.contant.MirujimaConstants;
import com.todo.mirujima_be.todo.dto.request.TodoListRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.todo.mirujima_be.todo.entity.QTodo.todo;

@RequiredArgsConstructor
public class CustomTodoRepositoryImpl implements CustomTodoRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<Long> getTodoIdList(TodoListRequest todoListRequest) {
        var goaldId = todoListRequest.getGoalId();
        var done = todoListRequest.getDone();
        var lastSeenId = todoListRequest.getLastSeenId();
        var pageSize = todoListRequest.getPageSize();
        if (pageSize == null) pageSize = MirujimaConstants.Todo.PAGE_SIZE;
        // 쿼리 조건 생성
        var condition = new BooleanBuilder();
        if (goaldId != null) {
            condition.and(todo.goal.id.eq(goaldId));
        }
        if (done != null) {
            condition.and(todo.done.eq(done));
        }
        if (lastSeenId != null) {
            condition.and(todo.id.lt(lastSeenId));
        }
        return factory.select(todo.id)
                .from(todo)
                .where(condition)
                .orderBy(todo.id.desc())
                .limit(pageSize)
                .fetch();
    }

}
