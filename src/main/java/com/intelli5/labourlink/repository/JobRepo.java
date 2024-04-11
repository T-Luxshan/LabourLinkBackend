package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Job,Long> {
}
