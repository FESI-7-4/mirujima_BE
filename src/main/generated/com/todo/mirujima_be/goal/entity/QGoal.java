package com.todo.mirujima_be.goal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoal is a Querydsl query type for Goal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoal extends EntityPathBase<Goal> {

    private static final long serialVersionUID = 740945532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoal goal = new QGoal("goal");

    public final com.todo.mirujima_be.common.entity.QBaseUserEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.todo.mirujima_be.user.entity.QUser lastCreatedUser;

    // inherited
    public final com.todo.mirujima_be.user.entity.QUser lastUpdatedUser;

    public final ListPath<com.todo.mirujima_be.todo.entity.Todo, com.todo.mirujima_be.todo.entity.QTodo> tasks = this.<com.todo.mirujima_be.todo.entity.Todo, com.todo.mirujima_be.todo.entity.QTodo>createList("tasks", com.todo.mirujima_be.todo.entity.Todo.class, com.todo.mirujima_be.todo.entity.QTodo.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QGoal(String variable) {
        this(Goal.class, forVariable(variable), INITS);
    }

    public QGoal(Path<? extends Goal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoal(PathMetadata metadata, PathInits inits) {
        this(Goal.class, metadata, inits);
    }

    public QGoal(Class<? extends Goal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.todo.mirujima_be.common.entity.QBaseUserEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.lastCreatedUser = _super.lastCreatedUser;
        this.lastUpdatedUser = _super.lastUpdatedUser;
        this.updatedAt = _super.updatedAt;
    }

}

