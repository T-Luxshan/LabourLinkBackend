package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.JobRole;
import lombok.Data;

import java.util.List;

@Data
public class LabourCardDTO {
    private String labourName;
    private List<JobRole> jobRole;
    private Double rating;
    private String labourEmail;
    private String profileUri;
}
