package com.likelion.besession.domain.contract.repository;

import com.likelion.besession.domain.contract.entity.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnalyseRepository extends JpaRepository<Analyse, Long> {
    List<Analyse> findByUserIdOrderByCreatedAtDesc(Long userId);
}