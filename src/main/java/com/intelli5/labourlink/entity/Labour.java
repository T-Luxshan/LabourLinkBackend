package com.intelli5.labourlink.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Labour extends User{

    @Column(name = "nic",nullable = false,unique = true)
    private String nic;

    @ElementCollection
    private List<String> jobRole;

}
