package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.AppointmentDTO;
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
@CrossOrigin("*")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    //------Appointment :- Appointment pending table------------------------
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getPendingAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getPendingAppointmentsWithDetails();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    //------Appointment :- Appointment Finished table------------------------
    @GetMapping("/deliver")
    public ResponseEntity<List<AppointmentDTO>> getDeliveredAppointmentsWithDetails() {
        List<AppointmentDTO> appointments = appointmentService.getDeliveredAppointmentsWithDetails();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    //------Appointment :- Appointment Cancel table------------------------
    @GetMapping("/cancel")
    public ResponseEntity<List<AppointmentDTO>> getCancelAppointmentsWithDetails() {
        List<AppointmentDTO> appointments = appointmentService.getCancelAppointmentsWithDetails();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    //----------------------Appointment - box 1- pending count------------------------
    @GetMapping("/pending_count")
    public ResponseEntity<Integer> getPendingAppointmentsCount() {
        List<AppointmentDTO> pendingAppointments = appointmentService.getPendingAppointmentsWithDetails();
        int count = pendingAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    //----------------------Appointment - box 3- delivered count------------------------

    @GetMapping("/delivered_count")
    public ResponseEntity<Integer> getDeliveredAppointmentsWithDetailsCount() {
        List<AppointmentDTO> deliveredAppointments = appointmentService.getDeliveredAppointmentsWithDetails();
        int count = deliveredAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
//----------------------Appointment - Revenue box 2-------------------------

    @GetMapping("/cancel_count")
    public ResponseEntity<Integer> getCancelAppointmentsWithDetailsCount() {
        List<AppointmentDTO> cancelAppointments = appointmentService.getCancelAppointmentsWithDetails();
        int count = cancelAppointments.size();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    //----------------------Appointment - Revenue box 4-------------------------
    @GetMapping("/total_revenue")
    public ResponseEntity<Double> sumByRevenue() {
            double totalRevenue = appointmentService.sumByRevenue();
            return ResponseEntity.ok(totalRevenue);

    }
    //-------------------------------------to dash board total Appointments --------------------------------
    @GetMapping("/total_app")
    public ResponseEntity<Integer> getTotalAppointmentsCount() {
        List<AppointmentDTO> cancelAppointments = appointmentService.getCancelAppointmentsWithDetails();
        List<AppointmentDTO> deliveredAppointments = appointmentService.getDeliveredAppointmentsWithDetails();
        List<AppointmentDTO> pendingAppointments = appointmentService.getPendingAppointmentsWithDetails();
        int totalAppointmentsCount = cancelAppointments.size() + deliveredAppointments.size() + pendingAppointments.size();
        return new ResponseEntity<>(totalAppointmentsCount, HttpStatus.OK);
    }

    //-----------------------------------Appointment : -graph left : -Job Vs Total Appointment--------------------------------
    @GetMapping("/graphleft")
    public List<Object[]> getJobVsTotalAppointment() {
        return appointmentService.jobVsTotalAppointment();
    }

    //-------------------------------------graph right : -Cancelled Job Vs Total--------------------------------
    @GetMapping("/graphright")
    public List<Object[]> findCancelledJobAppointmentCounts() {
        return appointmentService.findCancelledJobAppointmentCounts();
    }

    //------------------------------------Dashboard -graph  : - Active customer vs day-------------------------------
    @GetMapping("/dashboard/g_active")
    public List<Object[]> findActiveCustomerCount() {
        return appointmentService.findActiveCustomerCount();
    }

    //------------------------------------Dashboard -graph  : - Active labour vs day-------------------------------
    @GetMapping("/dashboard/g_active_l")
    public List<Object[]> findActiveLabourCount() {
        return appointmentService.findActiveLabourCount();
    }

    //------------------------------------Dashboard -line graph  : - Appointment count vs day-------------------------------

    @GetMapping("/dashboard/g_AppCount")
    public List<Object[]> findAppointmentsCountWithDay() {
        return appointmentService.findAppointmentsCountWithDay();
    }
}