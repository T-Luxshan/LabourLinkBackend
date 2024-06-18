package com.intelli5.labourlink.dto;


import com.intelli5.labourlink.entity.Status;
import com.intelli5.labourlink.entity.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String mobileNumber;
    private Status status;
    private LocalDate joinDate;
    private LocalTime joinTime;
    private UserRole role;

    public UserDTO(String name, String email, String mobileNumber, Status status) {
        this.name=name;
        this.email=email;
        this.mobileNumber=mobileNumber;
        this.status=status;
    }

//    public UserDTO(String name, String email,LocalDate joinDate, UserRole role) {
//
//    }
}