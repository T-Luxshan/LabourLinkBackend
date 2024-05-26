//Declare the package name
package com.intelli5.labourlink.entity;

//Import necessary JPA annotations for database mapping
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

//Import Lombok annotations to reduce boilerplate code
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Import the List interface from the Java Collection Frameworks
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Labour extends User{ //Defines the Labour class, which extends User
//@Builder
public class Labour extends User{

    @Column(name = "nic",nullable = false,unique = true) //Configures the nic field for database mapping
    private String nic;

    private String documentUri;

    @ElementCollection
    private List<String> jobRole;
}
