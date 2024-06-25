package com.intelli5.labourlink.dto;


import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.entity.Status;
import com.intelli5.labourlink.entity.UserRole;
import lombok.*;
import org.apache.tomcat.Jar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    private LocalDate joinDate=LocalDate.now();
//    private LocalTime joinTime=LocalTime.now();
    private UserRole role;
    private List<JobRole> jobRole;

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