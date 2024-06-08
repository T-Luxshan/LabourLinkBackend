package com.intelli5.labourlink.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabourLocationDTO {

    private Long id;
    private double latitude;
    private double longitude;
    private String labourId;
}