package com.todo.mirujima_be.note.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNote is a Querydsl query type for Note
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNote extends EntityPathBase<Note> {

    private static final long serialVersionUID = -1794442822L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNote note = new QNote("note");

    public final com.todo.mirujima_be.common.entity.QBaseUserEntity _super;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.todo.mirujima_be.user.entity.QUser lastCreatedUser;

    // inherited
    public final com.todo.mirujima_be.user.entity.QUser lastUpdatedUser;

    public final StringPath linkUrl = createString("linkUrl");

    public final StringPath title = createString("title");

    public final com.todo.mirujima_be.todo.entity.QTodo todo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QNote(String variable) {
        this(Note.class, forVariable(variable), INITS);
    }

    public QNote(Path<? extends Note> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNote(PathMetadata metadata, PathInits inits) {
        this(Note.class, metadata, inits);
    }

    public QNote(Class<? extends Note> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.todo.mirujima_be.common.entity.QBaseUserEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.lastCreatedUser = _super.lastCreatedUser;
        this.lastUpdatedUser = _super.lastUpdatedUser;
        this.todo = inits.isInitialized("todo") ? new com.todo.mirujima_be.todo.entity.QTodo(forProperty("todo"), inits.get("todo")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

