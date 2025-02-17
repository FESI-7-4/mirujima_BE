package com.todo.mirujima_be.todo.repository;

import static com.todo.mirujima_be.todo.entity.QTodo.todo;
import static com.todo.mirujima_be.user.entity.QUser.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todo.mirujima_be.auth.util.AuthUtil;
import com.todo.mirujima_be.common.contant.MirujimaConstants;
import com.todo.mirujima_be.todo.dto.request.TodoListRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomTodoRepositoryImpl implements CustomTodoRepository {

  private final JPAQueryFactory factory;

  @Override
  public List<Long> getTodoIdList(TodoListRequest todoListRequest) {
    var goalId = todoListRequest.getGoalId();
    var done = todoListRequest.getDone();
    var lastSeenId = todoListRequest.getLastSeenId();
    var pageSize = todoListRequest.getPageSize();
    if (pageSize == null) {
      pageSize = MirujimaConstants.Todo.PAGE_SIZE;
    }
    // 쿼리 조건 생성
    var condition = getCondition(goalId, done, lastSeenId);
    return factory.select(todo.id)
        .from(todo)
        .join(todo.createdBy, user)
        .where(condition)
        .orderBy(todo.id.desc())
        .limit(pageSize)
        .fetch();
  }

  @Override
  public Long getTodoCount(TodoListRequest todoListRequest) {
    var goalId = todoListRequest.getGoalId();
    var done = todoListRequest.getDone();
    var lastSeenId = todoListRequest.getLastSeenId();
    // 쿼리 조건 생성
    var condition = getCondition(goalId, done, lastSeenId);
    return factory.select(todo.count())
        .from(todo)
        .join(todo.createdBy, user)
        .where(condition)
        .fetchOne();
  }

  private BooleanBuilder getCondition(Long goalId, Boolean done, Long lastSeenId) {
    var condition = new BooleanBuilder();
    condition.and(todo.createdBy.email.eq(AuthUtil.getEmail()));
    if (goalId != null) {
      condition.and(todo.goal.id.eq(goalId));
    }
    if (done != null) {
      condition.and(todo.done.eq(done));
    }
    if (lastSeenId != null) {
      condition.and(todo.id.lt(lastSeenId));
    }
    return condition;
  }

}
