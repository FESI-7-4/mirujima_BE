package com.todo.mirujima_be.user.entity;

import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.user.dto.request.ModificationImageRequest;
import com.todo.mirujima_be.user.dto.request.ModificationRequest;
import com.todo.mirujima_be.user.dto.response.UserResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;

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

    private String orgFileName;
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

    public void modify(ModificationRequest modificationRequest, PasswordEncoder passwordEncoder) {
        if (modificationRequest.getEmail() != null) {
            this.email = modificationRequest.getEmail();
        }
        if (modificationRequest.getUsername() != null) {
            this.username = modificationRequest.getUsername();
        }
        if (modificationRequest.getPassword() != null) {
            this.password = passwordEncoder.encode(modificationRequest.getPassword());
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProfileImage(ModificationImageRequest modificationImageRequest) {
        var orgFileName = modificationImageRequest.getOrgFileName();
        var profileImagePath = modificationImageRequest.getProfileImagePath();
        if (profileImagePath != null) {
            if (StringUtils.isEmpty(orgFileName)) throw new AlertException("프로필 이미지 수정시 원본 파일 이름은 필수입니다.");
            this.orgFileName = orgFileName;
            this.profileImagePath = profileImagePath;
        } else {
            this.orgFileName = null;
            this.profileImagePath = null;
        }
    }

}
