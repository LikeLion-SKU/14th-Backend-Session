package com.likelion.besession.domain.post.entity;

import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.likelion.besession.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private  String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    public void updatePost(UpdatePostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void increaseViews() {
        this.viewCount++;
    }
    private LocalDateTime createdAt;
}