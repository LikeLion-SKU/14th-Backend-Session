package com.likjelion.besession.domain.post.entity;

import com.likjelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likjelion.besession.domain.user_v1.entity.UserV1;
import com.likjelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserV1 userV1;

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

