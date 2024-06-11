package com.intelli5.labourlink.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Labour extends User{ //Defines the Labour class, which extends User


    @Column(name = "nic",nullable = false,unique = true) //Configures the nic field for database mapping
    private String nic;

    private String documentUri;

    @ElementCollection
    @Enumerated(EnumType.STRING) // Specify the enum type
    private List<JobRole> jobRole;

    @OneToOne(mappedBy = "labour", cascade = CascadeType.ALL, orphanRemoval = true)
    private LabourProfile labourProfile;

}