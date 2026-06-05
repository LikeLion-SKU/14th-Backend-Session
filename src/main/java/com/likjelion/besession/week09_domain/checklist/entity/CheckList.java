package com.likjelion.besession.week09_domain.checklist.entity;

import com.likjelion.besession.global.common.BaseTimeEntity;
import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "checklist")
public class CheckList extends BaseTimeEntity {

    @Id @Column(name = "checklist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private ContractStatus contractStatus;


}
