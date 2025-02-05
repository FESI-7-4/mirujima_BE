package com.todo.mirujima_be.todo.entity;

import com.todo.mirujima_be.common.entity.BaseUserEntity;
import com.todo.mirujima_be.goal.entity.Goal;
import com.todo.mirujima_be.note.entity.Note;
import com.todo.mirujima_be.todo.dto.request.TodoModRequest;
import com.todo.mirujima_be.todo.dto.request.TodoRegRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "todo")
public class Todo extends BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;

    @Builder.Default
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    private String linkUrl;

    private String filePath;

    private Boolean done;

    public static Todo from(TodoRegRequest todoRegRequest) {
        return Todo.builder()
                .title(todoRegRequest.getTitle())
                .filePath(todoRegRequest.getFilePath())
                .done(false)
                .build();
    }

    public void modifyTo(TodoModRequest todoModRequest) {
        this.title = todoModRequest.getTitle();
        this.filePath = todoModRequest.getFilePath();
    }

    public void processCompletion(boolean isCompleted) {
        this.setDone(isCompleted);
    }

}