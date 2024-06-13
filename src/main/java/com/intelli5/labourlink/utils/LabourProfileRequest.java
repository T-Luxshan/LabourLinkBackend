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

public class LabourProfileRequest {
    private String aboutMe;
    private String gender;
    private List<String> languages;
//    private String location;
    private String labourEmail;
    private String experience;




}
