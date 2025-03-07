package com.todo.mirujima_be.auth.dto;

import lombok.Builder;

@Builder
public record OAuthUserInfo(String email, String name) {

}
