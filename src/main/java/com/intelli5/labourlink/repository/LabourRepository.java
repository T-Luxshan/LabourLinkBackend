package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("labourRepository")
public interface LabourRepository extends UserRepository{

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.nic = :nic")
    boolean existsByNic(@Param("nic") String nic);
}
