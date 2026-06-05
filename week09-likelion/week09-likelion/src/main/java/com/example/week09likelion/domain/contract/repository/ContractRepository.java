package com.example.week09likelion.domain.contract.repository;

import com.example.week09likelion.domain.contract.entity.Contract;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}