package com.likelion.besession.domain.property.entity;

import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "property")
@Getter
@NoArgsConstructor
public class Property extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Builder
    public Property(String address) {
        this.address = address;
    }
}
