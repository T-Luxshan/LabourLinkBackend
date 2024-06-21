package com.intelli5.labourlink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabourLocationDTO {

    private Long id;
    private double latitude;
    private double longitude;
    private String labourId;
    private String labourName;
    private double rating;

    public LabourLocationDTO(Long id, double latitude, double longitude, String labourId) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.labourId = labourId;
    }
}