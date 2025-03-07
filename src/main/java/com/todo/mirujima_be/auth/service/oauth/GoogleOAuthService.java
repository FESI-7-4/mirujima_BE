package com.todo.mirujima_be.auth.service.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.todo.mirujima_be.auth.dto.OAuthUserInfo;
import com.todo.mirujima_be.common.exception.AlertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleOAuthService implements OAuthService {

  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${google.tokenUrl}")
  private String tokenUrl;
  @Value("${google.userInfoUrl}")
  private String userInfoUrl;
  @Value("${google.client.id}")
  private String clientId;
  @Value("${google.client.secret}")
  private String clientSecret;
  @Value("${google.redirect.uri}")
  private String redirectUri;

  @Override
  public String getAccessToken(String code) {
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    var params = new LinkedMultiValueMap<String, String>();
    params.add("code", code);
    params.add("client_id", clientId);
    params.add("client_secret", clientSecret);
    params.add("redirect_uri", redirectUri);
    params.add("grant_type", "authorization_code");

    var request = new HttpEntity<>(params, headers);
    var response = restTemplate.postForEntity(tokenUrl, request, JsonNode.class);
    var responseBody = response.getBody();

    if (response.getStatusCode() != HttpStatus.OK || responseBody == null) {
      throw new AlertException("Google OAuth 인증 에러가 발생하였습니다.");
    }
    return responseBody.get("access_token").asText();
  }

  @Override
  public OAuthUserInfo getUserInfo(String accessToken) {
    var headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    var request = new HttpEntity<>(headers);
    var response = restTemplate.getForEntity(userInfoUrl, OAuthUserInfo.class, request);
    var responseBody = response.getBody();

    if (response.getStatusCode() != HttpStatus.OK || responseBody == null) {
      throw new AlertException("Google 회원 정보 조회중 에러가 발생하였습니다.");
    }
    return responseBody;
  }
}

