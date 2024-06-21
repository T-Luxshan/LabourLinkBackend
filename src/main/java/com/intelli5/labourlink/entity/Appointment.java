//package com.intelli5.labourlink.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Appointment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    private Long id;
//
//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(referencedColumnName = "email")
//    private Customer customer;
//
//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(referencedColumnName = "email")
//    private Labour labour;
//
//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(referencedColumnName = "jobId")
//    private Job job;
//
//    private String description;
//
//    private boolean isDelivered = false;
//
//    private boolean isCancelled = false;
//
//    private double taskRevenue;
//
//    private LocalDate appointmentFixedDate;
//    private LocalTime appointmentFixedTime;
//
//    private LocalDate AppointmentMadeDate = LocalDate.now();
//
//
//}
