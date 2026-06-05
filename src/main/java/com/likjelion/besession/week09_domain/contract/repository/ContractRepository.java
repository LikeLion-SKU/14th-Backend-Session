package com.likjelion.besession.week09_domain.contract.repository;

import com.likjelion.besession.week09_domain.contract.entity.Contract;
import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import com.likjelion.besession.week09_domain.contract.entity.Property;
import com.likjelion.besession.week09_domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findFirstByUserOrderByCreatedAtDesc(User user);

    List<Contract> findAllByUser(User user);

    Optional<Contract> findByProperty(Property property);

    @Modifying
    @Query("UPDATE Contract c SET c.contractStatus = :status WHERE c.id = :contractId")
    void updateStatus(@Param("contractId") Long contractId, @Param("status") ContractStatus status);

    @Modifying
    @Query("UPDATE Contract c SET c.processRate = :processRate WHERE c.id = :contractId")
    void updateProcessRate(@Param("contractId") Long contractId, @Param("processRate") int processRate);
}
