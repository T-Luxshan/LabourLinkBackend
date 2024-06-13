package com.intelli5.labourlink.utils;

import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.utils.validation.Password;
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
    @Password
    private String password;
    private String mobileNumber;
    private String address;
    private String nic;
    private String companyId;
    private List<JobRole> jobRole;
    private String documentUri;

}
