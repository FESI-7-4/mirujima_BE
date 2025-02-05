package com.todo.mirujima_be.auth.service;

import com.todo.mirujima_be.auth.dto.request.LoginRequest;
import com.todo.mirujima_be.auth.dto.request.RegisterRequest;
import com.todo.mirujima_be.auth.dto.response.EmailCheckResponse;
import com.todo.mirujima_be.auth.dto.response.LoginResponse;
import com.todo.mirujima_be.auth.dto.response.TokenResponse;
import com.todo.mirujima_be.auth.util.JwtUtil;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.user.entity.OauthPlatform;
import com.todo.mirujima_be.user.entity.User;
import com.todo.mirujima_be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findUserByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 이메일입니다."));


        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new AlertException("비밀번호가 일치하지 않습니다.");

        return LoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(jwtUtil.createAccessToken(loginRequest.getEmail()))
                .refreshToken(jwtUtil.createRefreshToken(loginRequest.getEmail()))
                .expiredAt(jwtUtil.getAccessTokenExpiredAt()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }

    @Transactional
    public LoginResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new AlertException("이메일이 중복되었습니다.");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .oauthPlatform(OauthPlatform.NONE)
                .build();

        userRepository.save(user);


        return LoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(jwtUtil.createAccessToken(registerRequest.getEmail()))
                .refreshToken(jwtUtil.createRefreshToken(registerRequest.getEmail()))
                .expiredAt(jwtUtil.getAccessTokenExpiredAt()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }

    @Transactional(readOnly = true)
    public TokenResponse refresh(String refreshToken) {
        String accessToken = jwtUtil.refreshAccessToken(refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(jwtUtil.getAccessTokenExpiredAt()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }

    @Transactional(readOnly = true)
    public EmailCheckResponse checkEmailExists(String email) {
        return new EmailCheckResponse(userRepository.existsUserByEmail(email));
    }
}
