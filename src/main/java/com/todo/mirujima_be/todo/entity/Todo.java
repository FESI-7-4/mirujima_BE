package com.todo.mirujima_be.todo.entity;

import com.todo.mirujima_be.common.entity.BaseUserEntity;
import com.todo.mirujima_be.goal.entity.Goal;
import com.todo.mirujima_be.note.entity.Note;
import com.todo.mirujima_be.todo.dto.request.TodoModRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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

  @OneToOne(mappedBy = "todo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Note note;

  @Column(nullable = false)
  private String title;

  private String linkUrl;

  private String filePath;

  @Column(nullable = false)
  private Boolean done;

  private Integer priority;

    private LocalDateTime completionDate;

  public void modifyTo(TodoModRequest todoModRequest) {
    this.title = todoModRequest.getTitle();
    this.filePath = todoModRequest.getFilePath();
    this.linkUrl = todoModRequest.getLinkUrl();
    this.done = todoModRequest.getDone();
    this.priority = todoModRequest.getPriority();
  }

}