package com.todo.mirujima_be.user.service;

import com.todo.mirujima_be.auth.dto.response.EmailCheckResponse;
import com.todo.mirujima_be.auth.util.AuthUtil;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.user.dto.request.ModificationImageRequest;
import com.todo.mirujima_be.user.dto.request.ModificationRequest;
import com.todo.mirujima_be.user.dto.request.RegisterRequest;
import com.todo.mirujima_be.user.dto.response.UserResponse;
import com.todo.mirujima_be.user.entity.OauthPlatform;
import com.todo.mirujima_be.user.entity.User;
import com.todo.mirujima_be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserResponse getUserInfo() {
        return UserResponse.of(AuthUtil.getUserInfo());
    }

    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        var email = registerRequest.getEmail();
        if (userRepository.existsUserByEmail(email)) {
            throw new AlertException("이메일이 중복되었습니다.");
        }
        var user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(email)
                .oauthPlatform(OauthPlatform.NONE)
                .build();
        userRepository.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Transactional
    public UserResponse modify(ModificationRequest modificationRequest) {
        var email = AuthUtil.getEmail();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new AlertException("유저를 찾지 못하였습니다"));
        user.modify(modificationRequest, passwordEncoder);
        return UserResponse.of(user);
    }

    @Transactional
    public UserResponse updateProfileImage(ModificationImageRequest modificationImageRequest) {
        var email = AuthUtil.getEmail();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new AlertException("유저를 찾지 못하였습니다"));
        user.updateProfileImage(modificationImageRequest);
        return UserResponse.of(user);
    }

    public EmailCheckResponse checkEmailExists(String email) {
        return new EmailCheckResponse(userRepository.existsUserByEmail(email));
    }
}
