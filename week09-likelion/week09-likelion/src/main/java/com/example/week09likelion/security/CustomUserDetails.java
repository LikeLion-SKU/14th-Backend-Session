package com.example.week09likelion.security;

import java.util.Collection;
import java.util.List;

import com.example.week09likelion.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    // User 엔티티를 Spring Security에서 사용할 수 있도록 함
    private final User user;

    // 사용자 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // email을 로그인 식별값으로 사용
    @Override
    public String getUsername() {
        return user.getEmail();
    }
}