package com.example.besession.domain.post.entity;

import com.example.besession.domain.post.dto.request.UpdatePostRequest;
import com.example.besession.domain.user.entity.User;
import com.example.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name="user_id")

    private User user;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private Long viewCount = 0L;

    // 조회수 증가
    public void increaseViewCount() {
        this.viewCount++;
    }

    //게시글 내용 갱신
    public void updatePost(UpdatePostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
