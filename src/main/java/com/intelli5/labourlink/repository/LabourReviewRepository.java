package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.LabourReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabourReviewRepository extends JpaRepository<LabourReview, Integer> {
    @Query("select lr from LabourReview lr where lr.labour=?1 and lr.jobRole=?2")
    List<LabourReview> findAllByEmailAndJobRole(Labour labour, String jobRole);

    List<LabourReview> findByLabour(Labour labour);

    @Query("SELECT ROUND(AVG(lr.rating),1) FROM LabourReview lr WHERE lr.labour = ?1")
    Double getRating(Labour labour);
}
