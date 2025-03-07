package com.todo.mirujima_be.auth.service;

import com.todo.mirujima_be.auth.dto.OAuthUserInfo;
import com.todo.mirujima_be.auth.service.oauth.GoogleOAuthService;
import com.todo.mirujima_be.auth.service.oauth.KakaoOAuthService;
import com.todo.mirujima_be.auth.service.oauth.OAuthService;
import com.todo.mirujima_be.user.entity.OauthPlatform;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthServiceFactory {

  private final Map<String, OAuthService> oAuthServiceMap;

  private final GoogleOAuthService googleOAuthService;
  private final KakaoOAuthService kakaoOAuthService;

  @PostConstruct
  public void init() {
    oAuthServiceMap.put(OauthPlatform.GOOGLE.name(), googleOAuthService);
    oAuthServiceMap.put(OauthPlatform.KAKAO.name(), kakaoOAuthService);
  }

  public OAuthUserInfo getUserInfo(String platform, String code) {
    var service = oAuthServiceMap.get(platform);
    var accessToken = service.getAccessToken(code);
    return service.getUserInfo(accessToken);
  }

}
