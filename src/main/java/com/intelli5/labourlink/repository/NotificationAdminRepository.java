package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.NotificationAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationAdminRepository extends JpaRepository<NotificationAdmin,Long> {
    List<NotificationAdmin> findByIdGreaterThan(Long id);
}
