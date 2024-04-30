package com.intelli5.labourlink.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {
    private String email;
    private String name;
    private String mobileNumber;
    private String role;

}

