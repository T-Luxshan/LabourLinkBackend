package com.intelli5.labourlink.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequest {
    private String title;
    private String description;
    private String reportedTo;
}
