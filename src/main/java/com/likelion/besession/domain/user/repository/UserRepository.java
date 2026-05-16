package com.likelion.besession.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.likelion.besession.domain.user.entity.User; // 패키지 경로는 환경에 맞게 확인하세요!

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일이 이미 존재하는지 확인 (회원가입 시 중복 검사용)
    boolean existsByEmail(String email);

    // 추가로 자주 쓰이는 메서드 (로그인 시 필요할 거예요)
    Optional<User> findByEmail(String email);
}