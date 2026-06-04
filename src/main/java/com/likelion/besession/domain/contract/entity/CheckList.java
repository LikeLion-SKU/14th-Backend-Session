package com.likelion.besession.domain.contract.entity;

import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checklist")
public class CheckList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Stage stage;

    @Column(nullable = false)
    private String title;

    @Column
    private String verificationMethod;

    @Column
    private String verificationUrl;

    @Column(columnDefinition = "TEXT")
    private String warningNote;

    @Column(nullable = false)
    private boolean isChecked;

    public void updateStatus(boolean isChecked) {
        this.isChecked = isChecked;
    }
}