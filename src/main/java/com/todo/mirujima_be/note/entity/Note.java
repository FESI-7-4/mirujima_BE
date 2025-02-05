package com.todo.mirujima_be.note.entity;

import com.todo.mirujima_be.common.entity.BaseUserEntity;
import com.todo.mirujima_be.note.dto.request.NoteModRequest;
import com.todo.mirujima_be.note.dto.request.NoteRegRequest;
import com.todo.mirujima_be.todo.entity.Todo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "note")
public class Note extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    private String linkUrl;

    public static Note from(NoteRegRequest noteRegRequest) {
        return Note.builder()
                .title(noteRegRequest.getTitle())
                .content(noteRegRequest.getContent())
                .linkUrl(noteRegRequest.getLinkUrl())
                .build();
    }

    public void modifyTo(NoteModRequest noteModRequest) {
        this.title = noteModRequest.getTitle();
        this.content = noteModRequest.getContent();
    }
}