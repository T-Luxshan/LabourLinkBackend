package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.entity.Status;
import com.intelli5.labourlink.entity.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAdminDTO {
    private String name;
    private String email;
    private String mobileNumber;
    private LocalDate joinDate;
    private UserRole role;
    private List<JobRole> jobRole;


}
