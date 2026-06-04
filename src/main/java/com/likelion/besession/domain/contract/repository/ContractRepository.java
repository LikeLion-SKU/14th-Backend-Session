package com.likelion.besession.domain.contract.repository;

import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    // 특정 사용자의 계약서를 최신 등록순으로 전체 조회
    List<Contract> findAllByUserOrderByCreatedDateDesc(User user);

    // 계약서 ID와 사용자를 함께 검증해서 본인 소유 계약서만 조회
    Optional<Contract> findByIdAndUser(Long id, User user);
}
