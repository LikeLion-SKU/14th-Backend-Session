package com.example.likelionbe.domain.contract.entity;

import com.example.likelionbe.domain.contract.enums.ChecklistCode;
import com.example.likelionbe.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "checklist")
public class Checklist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChecklistCode checklistCode;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Integer sortOrder;

    @Column(nullable = false)
    @Builder.Default
    private Boolean required = true;

    @Column(nullable = false)
    @Builder.Default
    private Boolean checked = false;

    private LocalDateTime checkedAt;

    /**
     * 체크리스트 항목의 체크 상태를 업데이트합니다.
     *
     * @param checked 체크 여부
     */
    public void updateChecked(Boolean checked) {
        this.checked = checked;
        this.checkedAt = Boolean.TRUE.equals(checked) ? LocalDateTime.now() : null;
    }
}
