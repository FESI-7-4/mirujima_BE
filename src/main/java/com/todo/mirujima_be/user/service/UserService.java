package com.todo.mirujima_be.user.service;

import com.todo.mirujima_be.auth.util.AuthUtil;
import com.todo.mirujima_be.user.dto.UserResponse;
import com.todo.mirujima_be.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    public UserResponse getUserInfo() {
        User userInfo = AuthUtil.getUserInfo();
        return userInfo.toResponse();
    }

}
