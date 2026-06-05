package com.likjelion.besession.domain.user_v1.repository;

import com.likjelion.besession.domain.user_v1.entity.UserV1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryV1 extends JpaRepository<UserV1, Long> {

    boolean existsByEmail(String email);

    Optional<UserV1> findByEmail(String email);

}
