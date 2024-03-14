package com.intelli5.labourlink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.apachecommons.CommonsLog;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends User {


    @Column(nullable = false)
    private String address;

}
