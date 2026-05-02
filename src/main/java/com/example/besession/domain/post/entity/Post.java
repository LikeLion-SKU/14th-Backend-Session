package com.example.besession.domain.post.entity;

import com.example.besession.domain.post.dto.request.UpdatePostRequest;
import com.example.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
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

    public void updatePost(UpdatePostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
