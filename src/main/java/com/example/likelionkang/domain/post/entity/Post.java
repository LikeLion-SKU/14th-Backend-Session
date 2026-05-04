package com.example.likelionkang.domain.post.entity;


import com.example.likelionkang.domain.global.common.BaseTimeEntitiy;
import com.example.likelionkang.domain.post.dto.request.UpdatePostRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Builder.Default
    @Column(nullable = false)
    private int viewCount = 0;

    public  void  updatePost(UpdatePostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    // 조회수 증가 로직 추가
    public void incrementViewCount() {
        this.viewCount++;
    }
}


