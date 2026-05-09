package com.wacaw.besession.domain.user.entity;

import com.wacaw.besession.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class User {

  @Entity
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
//  @Builder
  @Table(name = "user")
  public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String neme;
  }

}
