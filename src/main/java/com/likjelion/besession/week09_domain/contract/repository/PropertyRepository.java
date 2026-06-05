package com.likjelion.besession.week09_domain.contract.repository;

import com.likjelion.besession.week09_domain.contract.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
