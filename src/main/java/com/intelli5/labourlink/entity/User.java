package com.intelli5.labourlink.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;



@MappedSuperclass
@Data
//@Entity
//@Data
//@Inheritance(strategy = InheritanceType.JOINED)
//
public abstract class User implements Serializable {

    @Id
    @Column(unique = true,nullable = false)
    @Email(message = "Please enter valid email")
    private String email;

    @NotBlank(message = "Field can not be empty")
    private String name;

    @NotBlank(message = "Field can not be empty")
    @Size(min = 5, message = "The password must have at least 5 characters")
    private String password;

    @NotBlank(message = "Field can not be empty")
    @Column(unique = true)
    private String mobileNumber;

}
