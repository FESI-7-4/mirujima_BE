package com.todo.mirujima_be.note.entity;

import com.todo.mirujima_be.common.entity.BaseUserEntity;
import com.todo.mirujima_be.note.dto.request.NoteModRequest;
import com.todo.mirujima_be.note.dto.request.NoteRegRequest;
import com.todo.mirujima_be.todo.entity.Todo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

  @JoinColumn(name = "todo_id")
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Todo todo;

  @Column(nullable = false, length = 30)
  private String title;

  @Column(columnDefinition = "LONGTEXT", nullable = false)
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
    this.linkUrl = noteModRequest.getLinkUrl();
  }
}