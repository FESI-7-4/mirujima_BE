package com.todo.mirujima_be.auth.service;

import com.todo.mirujima_be.auth.dto.request.LoginRequest;
import com.todo.mirujima_be.auth.dto.response.LoginResponse;
import com.todo.mirujima_be.auth.dto.response.TokenResponse;
import com.todo.mirujima_be.auth.util.JwtUtil;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.user.dto.response.UserResponse;
import com.todo.mirujima_be.user.entity.OauthPlatform;
import com.todo.mirujima_be.user.entity.User;
import com.todo.mirujima_be.user.repository.UserRepository;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AuthService {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final GoogleOAuthService googleOAuthService;

  public LoginResponse login(LoginRequest loginRequest) {
    var email = loginRequest.getEmail();
    var user = userRepository.findUserByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 이메일입니다."));
      if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
          throw new AlertException("비밀번호가 일치하지 않습니다.");
      }
    var userDto = UserResponse.of(user);
    return LoginResponse.builder()
        .user(userDto)
        .accessToken(jwtUtil.createAccessToken(email))
        .refreshToken(jwtUtil.createRefreshToken(email))
        .expiredAt(jwtUtil.getExpiredAt())
        .build();
  }

  public TokenResponse refresh(String refreshToken) {
    var accessToken = jwtUtil.refreshAccessToken(refreshToken);
    return TokenResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .expiredAt(jwtUtil.getExpiredAt())
        .build();
  }

  @Transactional
  public LoginResponse loginOrRegister(String code) {
    var accessToken = googleOAuthService.getAccessToken(code);
    var userInfo = googleOAuthService.getUserInfo(accessToken);

    var email = userInfo.get("email").asText();
    var name = userInfo.get("name").asText();

    Optional<User> userOptional = userRepository.findUserByEmail(email);
    User user;

    if (userOptional.isPresent()) {
      user = userOptional.get();
    } else {
      user = User.builder()
          .email(email)
          .password(OauthPlatform.GOOGLE.name())
          .username(name)
          .oauthPlatform(OauthPlatform.GOOGLE)
          .build();
      userRepository.save(user);
    }
    var userDto = UserResponse.of(user);

    return LoginResponse.builder()
        .user(userDto)
        .accessToken(jwtUtil.createAccessToken(user.getEmail()))
        .refreshToken(jwtUtil.createRefreshToken(user.getEmail()))
        .expiredAt(jwtUtil.getAccessTokenExpiredAt()
            .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
        .build();
  }
}
