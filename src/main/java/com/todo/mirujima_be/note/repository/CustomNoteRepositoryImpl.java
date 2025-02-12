package com.todo.mirujima_be.note.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todo.mirujima_be.common.contant.MirujimaConstants;
import com.todo.mirujima_be.note.dto.request.NoteListRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.todo.mirujima_be.note.entity.QNote.note;
import static com.todo.mirujima_be.todo.entity.QTodo.todo;

@RequiredArgsConstructor
public class CustomNoteRepositoryImpl implements CustomNoteRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<Long> getNoteIdList(NoteListRequest noteListRequest) {
        var goalId = noteListRequest.getGoalId();
        var lastSeenId = noteListRequest.getLastSeenId();
        var pageSize = noteListRequest.getPageSize();
        if (pageSize == null) pageSize = MirujimaConstants.Note.PAGE_SIZE;
        // 쿼리 조건 생성
        var condition = getCondition(goalId, lastSeenId);
        return factory.select(note.id)
                .from(note)
                .join(note.todo, todo)
                .where(condition)
                .orderBy(note.id.desc())
                .limit(pageSize)
                .fetch();
    }

    @Override
    public Long getNoteCount(NoteListRequest noteListRequest) {
        var goalId = noteListRequest.getGoalId();
        var lastSeenId = noteListRequest.getLastSeenId();
        // 쿼리 조건 생성
        var condition = getCondition(goalId, lastSeenId);
        return factory.select(note.count())
                .from(note)
                .join(note.todo, todo)
                .where(condition)
                .fetchOne();
    }

    private BooleanBuilder getCondition(Long goalId, Long lastSeenId) {
        var condition = new BooleanBuilder();
        if (goalId != null) {
            condition.and(note.todo.goal.id.eq(goalId));
        }
        if (lastSeenId != null) {
            condition.and(note.id.lt(lastSeenId));
        }
        return condition;
    }

}
