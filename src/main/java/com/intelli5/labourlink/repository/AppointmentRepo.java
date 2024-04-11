package com.intelli5.labourlink.repository;

import com.intelli5.labourlink.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepo extends JpaRepository<Appointment,String> {

    @Query("SELECT SUM(a.taskRevenue) FROM Appointment a")
    Double sumTaskRevenue();


}