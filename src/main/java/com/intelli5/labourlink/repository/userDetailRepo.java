package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userDetailRepo extends JpaRepository<User,Long> {
}
