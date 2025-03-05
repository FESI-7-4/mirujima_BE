package com.todo.mirujima_be.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.todo.mirujima_be.common.exception.AlertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleOAuthService {

  private final RestTemplate restTemplate = new RestTemplate();
  @Value("${google.client.id}")
  private String clientId;
  @Value("${google.client.secret}")
  private String clientSecret;
  @Value("${google.redirect.uri}")
  private String redirectUri;

  public String getAccessToken(String code) {
    String tokenUrl = "https://oauth2.googleapis.com/token";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    String body = "code=" + code +
        "&client_id=" + clientId +
        "&client_secret=" + clientSecret +
        "&redirect_uri=" + redirectUri +
        "&grant_type=authorization_code";

    HttpEntity<String> request = new HttpEntity<>(body, headers);
    ResponseEntity<JsonNode> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, JsonNode.class);
    var responseBody = response.getBody();
    if (responseBody == null) {
      throw new AlertException("잘못된 Google OAuth 접근입니다.");
    }
    return responseBody.get("access_token").asText();
  }

  public JsonNode getUserInfo(String accessToken) {
    String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<Void> request = new HttpEntity<>(headers);
    ResponseEntity<JsonNode> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, JsonNode.class);
    var responseBody = response.getBody();
    if (responseBody == null) {
      throw new AlertException("잘못된 Google OAuth 접근입니다.");
    }
    return responseBody;
  }
}

