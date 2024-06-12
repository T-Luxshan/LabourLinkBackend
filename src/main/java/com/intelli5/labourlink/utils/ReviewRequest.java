package com.intelli5.labourlink.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    private String jobRole;
//    private String workTitle;
    private String description;
    private Double rating;
    private String labourEmail;
}
