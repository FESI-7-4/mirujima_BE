package com.todo.mirujima_be.user.entity;

import com.todo.mirujima_be.user.dto.request.ModificationRequest;
import com.todo.mirujima_be.user.dto.response.UserResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, length = 30, unique = true)
  private String email;
  @Column(nullable = false, length = 100)
  private String password;
  @Column(nullable = false, length = 30)
  private String username;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  @Enumerated(EnumType.STRING)
  private OauthPlatform oauthPlatform;

  private String profileImagePath;

  @Builder
  public User(String email, String password, String username, OauthPlatform oauthPlatform) {
    this.email = Objects.requireNonNull(email);
    this.password = Objects.requireNonNull(password);
    this.username = Objects.requireNonNull(username);
    this.oauthPlatform = Objects.requireNonNullElse(oauthPlatform, OauthPlatform.NONE);
  }

  public UserResponse toResponse() {
    return UserResponse.builder()
        .id(id)
        .email(email)
        .username(username)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();
  }

  public void modify(ModificationRequest modificationRequest) {
    this.email = modificationRequest.getEmail();
    this.password = modificationRequest.getPassword();
    this.username = modificationRequest.getUsername();
    this.profileImagePath = modificationRequest.getProfileImagePath();
    this.updatedAt = LocalDateTime.now();
  }

}
