package com.wacaw.besession.domain.post.entity;

import com.wacaw.besession.domain.post.dto.request.UpdatePostRequest;
import com.wacaw.besession.domain.user.entity.User.Users;
import com.wacaw.besession.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Users user;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(columnDefinition = "integer default 0", nullable = false)
  private int views;

  public void updatePost(UpdatePostRequest request) {
    this.title = request.getTitle();
    this.content = request.getContent();
  }
}
