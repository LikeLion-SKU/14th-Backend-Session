package com.likelion.besession.domain.user.repository;

import com.likelion.besession.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  //이메일이 중복 가입되지 않게 하기 위해 필요
  boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);

}
