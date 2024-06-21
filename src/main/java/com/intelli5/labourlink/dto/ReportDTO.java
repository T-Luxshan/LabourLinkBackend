package com.intelli5.labourlink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO implements Serializable {
    Integer id;
    String title;
    String description;
    String ReportedByName;
    String ReportedToName;
}