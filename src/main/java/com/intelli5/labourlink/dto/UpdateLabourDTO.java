package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.JobRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLabourDTO {
    private String name;
    private String nic;
    private String documentUri;
    private String email;
    private String mobileNumber;
    private List<JobRole> jobRole;
}
