package com.todo.mirujima_be.auth.service.oauth;

import com.todo.mirujima_be.auth.dto.OAuthUserInfo;

public interface OAuthService {

  String getAccessToken(String code, String redirectUri);

  OAuthUserInfo getUserInfo(String accessToken);

}
