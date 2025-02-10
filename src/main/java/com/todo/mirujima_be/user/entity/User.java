package com.todo.mirujima_be.user.entity;

import com.todo.mirujima_be.goal.entity.Goal;
import com.todo.mirujima_be.user.dto.request.ModificationRequest;
import com.todo.mirujima_be.user.dto.response.UserResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Goal> goals = new ArrayList<>();
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
