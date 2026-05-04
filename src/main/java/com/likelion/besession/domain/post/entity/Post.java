package com.likelion.besession.domain.post.entity;

import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
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

    @Column(name = "view_count")
    @Builder.Default
    private Long viewCount = 0L;

    public void updatePost(UpdatePostRequest updatePostRequest) {
        this.title = updatePostRequest.getTitle();
        this.content = updatePostRequest.getContent();
    }

    // 조회수 증가 로직
    public void viewPost(){
        this.viewCount++;
    }
}
