package com.likelion.besession.domain.contract.repository;

import com.likelion.besession.domain.contract.entity.Contract;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findByUser_IdOrderByCreatedAtDesc(Long userId);
}
