package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.EmailAdmin;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailAdminRepository extends JpaRepository<EmailAdmin,Long> {
    List<EmailAdmin> findBySubjectOrderByIdDesc(String Subject);
}
