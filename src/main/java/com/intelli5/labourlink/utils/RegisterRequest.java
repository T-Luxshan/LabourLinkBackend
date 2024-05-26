package com.intelli5.labourlink.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String mobileNumber;
    private String address;
    private String nic;
    private String companyId;
    private List<String> jobRole;
    private String documentUri;

}
