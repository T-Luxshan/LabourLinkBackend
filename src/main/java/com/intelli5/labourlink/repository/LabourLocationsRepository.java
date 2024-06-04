package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.LabourLocations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabourLocationsRepository extends JpaRepository<LabourLocations, Long> {
}
