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
public class UserIndividualDetail {
    private String name;
    private String email;
    private String mobileNumber;
    private LocalDate joinDate;
    private UserRole role;
}
