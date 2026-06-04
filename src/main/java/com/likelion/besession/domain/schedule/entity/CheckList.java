package com.likelion.besession.domain.schedule.entity;

import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checklist")
@Getter
@NoArgsConstructor
public class CheckList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 체크리스트가 속한 일정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    // 체크리스트 항목 제목
    @Column(nullable = false)
    private String title;

    // 완료 여부 (기본값 false)
    @Column(name = "is_checked", nullable = false)
    private boolean isChecked = false;

    @Builder
    public CheckList(Schedule schedule, String title) {
        this.schedule = schedule;
        this.title = title;
        this.isChecked = false;
    }

    // 완료/미완료 토글
    public void toggleChecked() {
        this.isChecked = !this.isChecked;
    }
}
