package com.intelli5.labourlink.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLabourProfileDTO {
    private String aboutMe;
    private String gender;
    private List<String> languages;
//    private String location;
}
