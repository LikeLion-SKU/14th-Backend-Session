package com.likelion.besession.domain.contractdocs.repository;

import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contractdocs.entity.ContractDocs;
import com.likelion.besession.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractDocsRepository extends JpaRepository<ContractDocs, Long> {

    // 계약서 존재 확인
    Optional<ContractDocs> findByContract(Contract contract);

    // 사용자의 전체 계약서 목록 조회
    List<ContractDocs> findAllByContractUser(User user);
}
