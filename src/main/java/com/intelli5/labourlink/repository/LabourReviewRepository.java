package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.LabourReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabourReviewRepository extends JpaRepository<LabourReview, Integer> {
}
