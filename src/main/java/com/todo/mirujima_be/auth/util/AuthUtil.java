package com.todo.mirujima_be.auth.util;

import com.todo.mirujima_be.auth.entity.CustomUserDetail;
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

}
