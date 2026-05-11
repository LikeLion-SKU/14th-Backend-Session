package com.likelion.besession.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.besession.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmail(String email);
}

