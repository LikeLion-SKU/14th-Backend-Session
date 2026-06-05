package com.example.week09likelion.domain.schedule.entity;

import com.example.week09likelion.domain.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "contract_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContractSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ScheduleStage stage;

    @Column(nullable = false, length = 100)
    private String title;

    private LocalDate dueDate;

    @Column(nullable = false)
    private Boolean isCompleted;

    @Builder
    public ContractSchedule(Contract contract, ScheduleStage stage, String title, LocalDate dueDate) {
        this.contract = contract;
        this.stage = stage;
        this.title = title;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    // 계약 일정 수정
    public void updateSchedule(ScheduleStage stage, String title, LocalDate dueDate, Boolean isCompleted) {
        this.stage = stage;
        this.title = title;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }
}