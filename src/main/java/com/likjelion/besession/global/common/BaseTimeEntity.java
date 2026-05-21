package com.likjelion.besession.global.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


/**
 * 처음 엔티티가 생성되면 jpa이벤트 발생 -> AuditingEntityListener 동작
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 생성과 수정을 감지할수있게하는 에노테이션
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
