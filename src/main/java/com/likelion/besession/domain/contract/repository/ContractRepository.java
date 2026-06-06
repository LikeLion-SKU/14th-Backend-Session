package com.likelion.besession.domain.contract.repository;

import com.likelion.besession.domain.contract.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
