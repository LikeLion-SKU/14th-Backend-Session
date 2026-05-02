package com.likjelion.besession.domain.post.entity;

import com.likjelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likjelion.besession.global.common.BaseTimeEntity;
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

    @Column(name =  "view", nullable = false)
    @Builder.Default
    private int view = 0;



    public Post updatePost(UpdatePostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();

        return this;
    }

}

