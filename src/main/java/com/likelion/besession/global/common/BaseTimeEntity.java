package com.likelion.besession.global.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 이걸 상속받는 클래스에서 실제 테이블의 컬럼으로 만들어줌.
@EntityListeners(AuditingEntityListener.class) // 누가 데이터를 건드리는지 확인.
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
