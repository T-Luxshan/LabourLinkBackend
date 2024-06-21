package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.UserRole;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserIndividualDetailDTO {
    private String name;
    private String email;
    private String mobileNumber;
    private LocalDate joinDate;
    private UserRole role;
}
