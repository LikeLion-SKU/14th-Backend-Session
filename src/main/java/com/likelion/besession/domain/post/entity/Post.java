package com.likelion.besession.domain.post.entity;

import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor // JPA가 기본 생성자 생성을 필수로 요구
@AllArgsConstructor
@Table(name = "post")
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
    // nullable = false : null을 넣어줄 수 없다.
    // 생성자, Getter, Builder (lombok)

    @Column(nullable = false)
    @Builder.Default // 기본값을 0L으로
    private long viewCount = 0L; // long은 null을 받을 수 없음.

    // 조회수 증가 로직
    public void increaseViewCount() {
        this.viewCount++;
    }

    public void updatePost(UpdatePostRequest request){
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
