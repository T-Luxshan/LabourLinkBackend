//package com.intelli5.labourlink.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Job {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    private Long jobId;
//
//    private String jobName;
//    private String description;
//    @ManyToMany(mappedBy = "jobs")
//    private Set<Labour> labours = new HashSet<>();
//
//    @OneToMany(mappedBy = "job")
//    private List<Appointment> appointment;
//
//
//}
