package com.todo.mirujima_be.auth.service;

import com.todo.mirujima_be.auth.dto.OAuthUserInfo;
import com.todo.mirujima_be.auth.service.oauth.GoogleOAuthService;
import com.todo.mirujima_be.auth.service.oauth.KakaoOAuthService;
import com.todo.mirujima_be.auth.service.oauth.OAuthService;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.user.entity.OauthPlatform;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthServiceFactory {

  private final Map<String, OAuthService> oAuthServiceMap = new HashMap<>();

  private final GoogleOAuthService googleOAuthService;
  private final KakaoOAuthService kakaoOAuthService;

  @PostConstruct
  public void init() {
    oAuthServiceMap.put(OauthPlatform.GOOGLE.name(), googleOAuthService);
    oAuthServiceMap.put(OauthPlatform.KAKAO.name(), kakaoOAuthService);
  }

  public OAuthUserInfo getUserInfo(String platform, String code, String redirectUri) {
    var service = Optional.ofNullable(oAuthServiceMap.get(platform))
        .orElseThrow(() -> new AlertException("잘못된 인증 플랫폼입니다."));
    var accessToken = service.getAccessToken(redirectUri, code);
    return service.getUserInfo(accessToken);
  }

}
