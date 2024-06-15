package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.LabourLocations;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabourLocationsRepository extends JpaRepository<LabourLocations, Long> {
    List<LabourLocations> findByLabourJobRole(JobRole jobRole);

    LabourLocations findByLabour(Labour labour);
}
