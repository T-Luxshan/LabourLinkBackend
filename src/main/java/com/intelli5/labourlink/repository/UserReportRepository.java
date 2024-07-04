package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.dto.ReportDTO;
import com.intelli5.labourlink.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Integer> {
@Query("select ur from UserReport ur where ur.ReportedBy.email = :email or ur.ReportedTo.email = :email")
    List<UserReport> findByLabourOrCustomer(String email);

    List<UserReport> findByIdGreaterThan(Integer id);
}
