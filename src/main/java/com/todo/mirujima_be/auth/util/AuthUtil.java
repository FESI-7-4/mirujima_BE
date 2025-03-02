package com.todo.mirujima_be.auth.util;

import com.todo.mirujima_be.auth.entity.CustomUserDetail;
import com.todo.mirujima_be.common.entity.BaseUserEntity;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static User getUserInfo() {
        try {
            CustomUserDetail userDetails = (CustomUserDetail) getAuthentication().getPrincipal();
            return userDetails.getUser();
        } catch (Exception e) {
            throw new AlertException("로그인이 필요합니다.");
        }
    }

    public static String getEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return StringUtils.isEmpty(email) ? "" : email;
    }

    public static <T extends BaseUserEntity> void checkAuthority(String email, String type, T entity) {
        if (!entity.getCreatedBy().getEmail().equals(email)) {
            throw new AlertException(type + "에 대한 권한이 없습니다.");
        }
    }

}
