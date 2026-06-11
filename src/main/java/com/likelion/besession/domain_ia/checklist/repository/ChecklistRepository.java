package com.likelion.besession.domain_ia.checklist.repository;

import com.likelion.besession.domain_ia.checklist.entity.Checklist;
import org.springframework.data.repository.CrudRepository;

public interface ChecklistRepository extends CrudRepository<Checklist, Long> {
}
