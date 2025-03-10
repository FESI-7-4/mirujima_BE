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
public class KakaoOAuthService implements OAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.tokenUrl}")
    private String tokenUrl;
    @Value("${kakao.userInfoUrl}")
    private String userInfoUrl;
    @Value("${kakao.client.id}")
    private String clientId;
    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Override
    public String getAccessToken(String code, String redirectUri) {
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
            throw new AlertException("Kakao OAuth 인증 에러가 발생하였습니다.");
        }
        return responseBody.get("access_token").asText();
    }

    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        var headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var request = new HttpEntity<>(headers);
        var response = restTemplate.postForEntity(userInfoUrl, request, JsonNode.class);
        var responseBody = response.getBody();

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AlertException("Kakao 회원 정보 조회중 에러가 발생하였습니다.");
        }

        return OAuthUserInfo.builder()
                .email(responseBody.get("kakao_account").get("email").asText())
                .name(responseBody.get("properties").get("nickname").asText())
                .build();
    }
}
