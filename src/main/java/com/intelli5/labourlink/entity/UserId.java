package com.intelli5.labourlink.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
@Embeddable
public class UserId implements Serializable {

    @Column(unique = true,nullable = false)
    @Email(message = "Please enter valid email")
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
