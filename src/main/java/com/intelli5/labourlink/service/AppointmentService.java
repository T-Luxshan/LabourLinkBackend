package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.AppointmentDTO;
import com.intelli5.labourlink.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    /*List<Appointment> findAll();*/

    List<AppointmentDTO> getPendingAppointmentsWithDetails();
    List<AppointmentDTO> getDeliveredAppointmentsWithDetails();
    List<AppointmentDTO>  getCancelAppointmentsWithDetails();

    double sumByRevenue();


}
