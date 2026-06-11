package com.likelion.besession.domain_ia.contract.repository;

import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain_ia.contract.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findAllByUserOrderByCreatedDateDesc(User user);

    Contract findByUserAndIsDoneFalse(User user);
}
