package com.likjelion.besession.week09_domain.checklist.repository;

import com.likjelion.besession.week09_domain.checklist.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {
}
