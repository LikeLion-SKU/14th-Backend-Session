package com.likelion.besession.domain_ia.checklist.entity;

import com.likelion.besession.domain_ia.contract.entity.Process;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Checklist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Process process;

    private String name;

    private String content;


    public Checklist(String name, String content, Process process) {
        this.process = process;
        this.name = name;
        this.content = content;
    }
}
