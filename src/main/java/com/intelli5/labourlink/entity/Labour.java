//Declare the package name
package com.intelli5.labourlink.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Labour extends User{ //Defines the Labour class, which extends User


    @Column(name = "nic",nullable = false,unique = true) //Configures the nic field for database mapping
    private String nic;


    private String documentUri;

    @ElementCollection
    @Enumerated(EnumType.STRING) // Specify the enum type
    private List<JobRole> jobRole;

    @OneToOne(mappedBy = "labour", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private LabourLocations labourLocations;

    @OneToMany(mappedBy = "labour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "labour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabourReview> labourReview;



    @OneToOne(mappedBy = "labour", cascade = CascadeType.ALL, orphanRemoval = true)
    private LabourProfile labourProfile;


}