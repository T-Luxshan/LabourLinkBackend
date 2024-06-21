package com.intelli5.labourlink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

//
///**
// * DTO for {@link com.intelli5.labourlink.entity.LabourReview}
// */
//@Value
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO implements Serializable {
    Integer Id;
    String jobRole;
//    String workTitle;
    String description;
    Double rating;
    String customerName;
    String labourName;
    String customerEmail;
    private LocalDateTime reviewPostAt;


}