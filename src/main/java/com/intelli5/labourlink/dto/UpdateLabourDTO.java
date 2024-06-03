package com.intelli5.labourlink.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLabourDTO {
    private String name;
//    private String address;
    private String mobileNumber;
}
