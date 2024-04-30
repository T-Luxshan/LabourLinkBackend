package com.intelli5.labourlink.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private String email;
    private String name;
    private String mobileNumber;
    private String role;
}
