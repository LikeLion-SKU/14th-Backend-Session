package com.likelion.besession.domain.schedule.entity;

import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
@NoArgsConstructor
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일정 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 계약 진행 단계 (예: "계약 체결", "잔금 납부")
    @Column(nullable = false)
    private String stage;

    // 일정 제목
    @Column(nullable = false)
    private String title;

    // 일정 날짜
    @Column(nullable = false)
    private LocalDate date;

    // 경고 알림 시각 (nullable: 알림 설정을 안 할 수도 있음)
    @Column(name = "warning_at")
    private LocalDateTime warningAt;

    // 해당 일정에 속한 체크리스트 목록 (Schedule 삭제 시 함께 삭제)
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckList> checkLists = new ArrayList<>();

    @Builder
    public Schedule(User user, String stage, String title, LocalDate date, LocalDateTime warningAt) {
        this.user = user;
        this.stage = stage;
        this.title = title;
        this.date = date;
        this.warningAt = warningAt;
    }
}
