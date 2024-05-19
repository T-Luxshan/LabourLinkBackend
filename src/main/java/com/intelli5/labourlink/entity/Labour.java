package com.intelli5.labourlink.entity;

import com.intelli5.labourlink.Enum.UserRole;
import jakarta.persistence.*;

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
//@Builder
public class Labour extends User{

    @Column(name = "nic",nullable = false,unique = true)
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



}
