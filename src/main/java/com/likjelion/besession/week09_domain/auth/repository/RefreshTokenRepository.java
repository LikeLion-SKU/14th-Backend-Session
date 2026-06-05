package com.likjelion.besession.week09_domain.auth.repository;


import com.likjelion.besession.week09_domain.auth.entity.RefreshToken;
import com.likjelion.besession.week09_domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {


    Optional<RefreshToken> findByUser(User user);
}
