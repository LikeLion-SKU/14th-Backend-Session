package com.likjelion.besession.week09_domain.user.repository;

import com.likjelion.besession.week09_domain.user.entity.Gender;
import com.likjelion.besession.week09_domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.gender = :gender, u.img = :img WHERE u.id = :id")
    void updateProfile(@Param("id") Long id, @Param("name") String name, @Param("gender") Gender gender, @Param("img") String img);
}
