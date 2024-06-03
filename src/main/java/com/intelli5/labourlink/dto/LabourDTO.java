package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.JobRole;
import lombok.Data;
import java.util.List;

@Data
public class LabourDTO {
    private String name;
    private String nic;
    private String documentUri;
    private String email;
    private String mobileNumber;
    private List<JobRole> jobRole;
//    private String status;
}
