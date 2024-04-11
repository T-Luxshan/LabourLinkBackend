package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.AppointmentDTO;
import com.intelli5.labourlink.entity.Appointment;
import com.intelli5.labourlink.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {
    @Autowired

    private AppointmentService appointmentService;
    @GetMapping
        public ResponseEntity<List<AppointmentDTO>> getAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getPendingAppointmentsWithDetails();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
    @GetMapping("/deliver")
    public ResponseEntity<List<AppointmentDTO>> getDeliveredAppointmentsWithDetails(){
        List<AppointmentDTO> appointments = appointmentService.getDeliveredAppointmentsWithDetails();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/cancel")
    public ResponseEntity<List<AppointmentDTO>> getCancelAppointmentsWithDetails(){
        List<AppointmentDTO> appointments = appointmentService.getCancelAppointmentsWithDetails();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/pending_count")
    public ResponseEntity<Integer> getPendingAppointmentsCount() {
        List<AppointmentDTO> pendingAppointments = appointmentService.getPendingAppointmentsWithDetails();
        int count = pendingAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    @GetMapping("/delivered_count")
    public ResponseEntity<Integer> getDeliveredAppointmentsWithDetailsCount() {
        List<AppointmentDTO> deliveredAppointments = appointmentService.getDeliveredAppointmentsWithDetails();
        int count = deliveredAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/cancel_count")
    public ResponseEntity<Integer> getCancelAppointmentsWithDetailsCount() {
        List<AppointmentDTO> cancelAppointments = appointmentService.getCancelAppointmentsWithDetails();
        int count = cancelAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/total_revenue")
    public double sumByRevenue(){
        return appointmentService.sumByRevenue();
    }
//to dash board total Appointments --------------------------------
@GetMapping("/total_app")
public ResponseEntity<Integer> getTotalAppointmentsCount() {
    List<AppointmentDTO> cancelAppointments = appointmentService.getCancelAppointmentsWithDetails();
    List<AppointmentDTO> deliveredAppointments = appointmentService.getDeliveredAppointmentsWithDetails();
    List<AppointmentDTO> pendingAppointments = appointmentService.getPendingAppointmentsWithDetails();

    int totalAppointmentsCount = cancelAppointments.size() + deliveredAppointments.size() + pendingAppointments.size();

    return new ResponseEntity<>(totalAppointmentsCount, HttpStatus.OK);
}


}
