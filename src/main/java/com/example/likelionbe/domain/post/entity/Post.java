package com.example.likelionbe.domain.post.entity;

import com.example.likelionbe.domain.user.entity.User;
import com.example.likelionbe.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void viewPost(){
        this.viewCount++;
    }
}
