package com.likelion.besession.domain.contract.repository;

import com.likelion.besession.domain.contract.entity.CheckList;
import com.likelion.besession.domain.contract.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    List<CheckList> findByContractIdAndStage(Long contractId, Stage stage);
}