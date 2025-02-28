package com.todo.mirujima_be.note.repository;

import static com.todo.mirujima_be.goal.entity.QGoal.goal;
import static com.todo.mirujima_be.note.entity.QNote.note;
import static com.todo.mirujima_be.todo.entity.QTodo.todo;
import static com.todo.mirujima_be.user.entity.QUser.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todo.mirujima_be.auth.util.AuthUtil;
import com.todo.mirujima_be.common.contant.MirujimaConstants;
import com.todo.mirujima_be.note.dto.request.NoteListRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomNoteRepositoryImpl implements CustomNoteRepository {

  private final JPAQueryFactory factory;

  @Override
  public List<Long> getNoteIdList(NoteListRequest noteListRequest) {
    var goalId = noteListRequest.getGoalId();
    var lastSeenId = noteListRequest.getLastSeenId();
    var hasGoal = noteListRequest.getHasGoal();
    var pageSize = noteListRequest.getPageSize();
    if (pageSize == null) {
      pageSize = MirujimaConstants.Note.PAGE_SIZE;
    }
    // 쿼리 조건 생성
    var condition = getCondition(goalId, lastSeenId, hasGoal);
    return factory.select(note.id)
        .from(note)
        .join(note.todo, todo)
        .leftJoin(todo.goal, goal)
        .join(note.createdBy, user)
        .where(condition)
        .orderBy(note.id.desc())
        .limit(pageSize)
        .fetch();
  }

  @Override
  public Long getNoteCount(NoteListRequest noteListRequest) {
    var goalId = noteListRequest.getGoalId();
    var lastSeenId = noteListRequest.getLastSeenId();
    var hasGoal = noteListRequest.getHasGoal();
    // 쿼리 조건 생성
    var condition = getCondition(goalId, lastSeenId, hasGoal);
    return factory.select(note.count())
        .from(note)
        .join(note.todo, todo)
        .leftJoin(todo.goal, goal)
        .join(note.createdBy, user)
        .where(condition)
        .fetchOne();
  }

  private BooleanBuilder getCondition(Long goalId, Long lastSeenId, Boolean hasGoal) {
    var condition = new BooleanBuilder();
    condition.and(note.createdBy.email.eq(AuthUtil.getEmail()));
    if (goalId != null) {
      condition.and(note.todo.goal.id.eq(goalId));
    }
    if (lastSeenId != null) {
      condition.and(note.id.lt(lastSeenId));
    }
    if (hasGoal != null) {
      if (hasGoal) {
        condition.and(goal.id.isNotNull());
      } else {
        condition.and(goal.id.isNull());
      }
    }
    return condition;
  }

}
