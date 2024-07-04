package com.intelli5.labourlink.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SuspendUser  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotNull
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

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDate joinDate;
    private LocalTime joinTime;
    @Column(name = "is_present")
    private boolean isPresent =true;
    //    @Enumerated(EnumType.STRING)
//    UserRole role;

//    @OneToOne(mappedBy = "suspendUser")
//    private RefreshToken refreshToken;

    private boolean isVerified = true;
    private boolean isEnabled = true;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private String reason;
    private LocalDate suspendedDate;
    private LocalTime suspendedTime;

}
