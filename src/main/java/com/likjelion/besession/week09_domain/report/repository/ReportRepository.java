package com.likjelion.besession.week09_domain.report.repository;

import com.likjelion.besession.week09_domain.contract.entity.Property;
import com.likjelion.besession.week09_domain.report.entity.Report;
import com.likjelion.besession.week09_domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByUser(User user);
    Optional<Report> findByProperty(Property property);
}
