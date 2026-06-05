package com.likjelion.besession.week09_domain.checklist.repository;

import com.likjelion.besession.week09_domain.checklist.entity.ContractCheckList;
import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractCheckListRepository extends JpaRepository<ContractCheckList, Long> {

    List<ContractCheckList> findAllByContractId(Long contractId);

    List<ContractCheckList> findAllByContractIdAndContractStatus(Long contractId, ContractStatus contractStatus);

    long countByContractId(Long contractId);

    long countByContractIdAndCheckedTrue(Long contractId);

    @Modifying
    @Query("UPDATE ContractCheckList ccl SET ccl.checked = :checked WHERE ccl.id = :id")
    void updateChecked(@Param("id") Long id, @Param("checked") boolean checked);
}
