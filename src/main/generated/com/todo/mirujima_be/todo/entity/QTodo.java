package com.todo.mirujima_be.todo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTodo is a Querydsl query type for Todo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTodo extends EntityPathBase<Todo> {

    private static final long serialVersionUID = 297438306L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTodo todo = new QTodo("todo");

    public final com.todo.mirujima_be.common.entity.QBaseUserEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final BooleanPath done = createBoolean("done");

    public final StringPath filePath = createString("filePath");

    public final com.todo.mirujima_be.goal.entity.QGoal goal;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.todo.mirujima_be.user.entity.QUser lastCreatedUser;

    // inherited
    public final com.todo.mirujima_be.user.entity.QUser lastUpdatedUser;

    public final StringPath linkUrl = createString("linkUrl");

    public final ListPath<com.todo.mirujima_be.note.entity.Note, com.todo.mirujima_be.note.entity.QNote> notes = this.<com.todo.mirujima_be.note.entity.Note, com.todo.mirujima_be.note.entity.QNote>createList("notes", com.todo.mirujima_be.note.entity.Note.class, com.todo.mirujima_be.note.entity.QNote.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QTodo(String variable) {
        this(Todo.class, forVariable(variable), INITS);
    }

    public QTodo(Path<? extends Todo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTodo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTodo(PathMetadata metadata, PathInits inits) {
        this(Todo.class, metadata, inits);
    }

    public QTodo(Class<? extends Todo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.todo.mirujima_be.common.entity.QBaseUserEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.goal = inits.isInitialized("goal") ? new com.todo.mirujima_be.goal.entity.QGoal(forProperty("goal"), inits.get("goal")) : null;
        this.lastCreatedUser = _super.lastCreatedUser;
        this.lastUpdatedUser = _super.lastUpdatedUser;
        this.updatedAt = _super.updatedAt;
    }

}

