package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.LabourReview;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("labourRepository")
public interface LabourRepository extends UserRepository{

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM Labour l WHERE l.nic = :nic")
    boolean existsByNic(@Param("nic") String nic);

    @Query("SELECT l FROM Labour l WHERE l.email = :email")
    Labour findLabour(String email);


//
}
