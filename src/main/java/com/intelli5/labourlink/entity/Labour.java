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
//@Builder
public class Labour extends User{

    @Column(name = "nic",nullable = false,unique = true)
    private String nic;

    private String documentUri;

    @ElementCollection
    private List<String> jobRole;

    @OneToOne(mappedBy = "labour", cascade = CascadeType.ALL)
    private LabourLocations labourLocation;
}
