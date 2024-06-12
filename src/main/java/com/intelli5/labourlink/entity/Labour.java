//Declare the package name
package com.intelli5.labourlink.entity;


import jakarta.persistence.*;
//Import necessary JPA annotations for database mapping
import jakarta.persistence.*;

//Import Lombok annotations to reduce boilerplate code
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Labour extends User{ //Defines the Labour class, which extends User


    @Column(name = "nic",nullable = false,unique = true) //Configures the nic field for database mapping
    private String nic;
/*
    @Lob
    private byte[] pdfDocument;*/
    @ManyToMany
    @JoinTable(
            name = "Labour_Job",
            joinColumns = @JoinColumn(name = "labour_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id",referencedColumnName = "jobId")
    )
    private Set<Job> jobs = new HashSet<>();

    @OneToMany(mappedBy = "labour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointment;



    private String documentUri;

    @ElementCollection
    @Enumerated(EnumType.STRING) // Specify the enum type
    private List<JobRole> jobRole;

}