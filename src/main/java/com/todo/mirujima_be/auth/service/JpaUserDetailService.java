package com.todo.mirujima_be.auth.service;

import com.todo.mirujima_be.auth.entity.CustomUserDetail;
import com.todo.mirujima_be.user.entity.User;
import com.todo.mirujima_be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userByUsername = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾지 못했습니다. : " + username));
        return new CustomUserDetail(userByUsername);
    }
}
