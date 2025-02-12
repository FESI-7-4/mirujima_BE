package com.todo.mirujima_be.todo.entity;

import com.todo.mirujima_be.common.entity.BaseUserEntity;
import com.todo.mirujima_be.goal.entity.Goal;
import com.todo.mirujima_be.note.entity.Note;
import com.todo.mirujima_be.todo.dto.request.TodoModRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @OneToOne(mappedBy = "todo", fetch = FetchType.EAGER)
    private Note note;

    @Column(nullable = false)
    private String title;

    private String linkUrl;

    private String filePath;

    @Column(nullable = false)
    private Boolean done;

    private Integer priority;

    public void modifyTo(TodoModRequest todoModRequest) {
        this.title = todoModRequest.getTitle();
        this.filePath = todoModRequest.getFilePath();
        this.linkUrl = todoModRequest.getLinkUrl();
        this.done = todoModRequest.getDone();
        this.priority = todoModRequest.getPriority();
    }

}