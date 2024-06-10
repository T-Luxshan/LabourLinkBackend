package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
    boolean existsByJobName(String jobName);
   /* @Query(value = "SELECT COUNT(l.labour_Id), j.jobName " +
            "FROM Labour_Job l JOIN Job j ON l.jobId = j.jobId " +
            "GROUP BY l.jobId", nativeQuery = true)
    List<Object[]> getLabourJobCountsWithId();*/

    //...............................dashboard - pie chart -------------------------------
   /* @Query(
            "SELECT j.jobName, COUNT(a.id) " +
                    "FROM Appointment a JOIN Job j ON a.id = j.jobId " +
                    "WHERE a.AppointmentMadeDate = :startDate " +
                    "GROUP BY j.jobName"
    )
    List<Object[]> demandJobDetail(@Param("startDate") LocalDate startDate);
*/

}
