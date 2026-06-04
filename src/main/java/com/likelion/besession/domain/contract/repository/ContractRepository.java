package com.likelion.besession.domain.contract.repository;

import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    // 자기 자신의 계약 전체 목록 조회
    List<Contract> findAllByUser(User user);

    // 계약 단건 조회
    Optional<Contract> findByContractIdAndUser(Long contractId, User user);
}
