package com.likjelion.besession.global.sequrity;

import com.likjelion.besession.domain.user_v1.entity.UserV1;
import com.likjelion.besession.domain.user_v1.repository.UserRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsServiceV1 implements UserDetailsService {

    private final UserRepositoryV1 userRepositoryV1;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserV1 userV1 = userRepositoryV1
                        .findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new CustomUserDetailsV1(userV1);
    }

    public CustomUserDetailsV1 loadUserById(Long userId) {
        UserV1 userV1 = userRepositoryV1
                        .findById(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new CustomUserDetailsV1(userV1);
    }
}
