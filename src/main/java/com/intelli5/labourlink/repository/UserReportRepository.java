package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Integer> {
}
