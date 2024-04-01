package com.intelli5.labourlink.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class Admin extends User{

    @Column(nullable = false)
    private String companyId;

}
