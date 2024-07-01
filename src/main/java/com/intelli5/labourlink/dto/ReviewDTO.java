package com.intelli5.labourlink.dto;

import lombok.*;

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
    String labourName;
    String customerName;
    String customerEmail;
    private LocalDateTime reviewPostAt;
    String labourId;

    public ReviewDTO(Integer id, String jobRole, String description, Double rating, String labourName, String customerName, String customerEmail, LocalDateTime reviewPostAt) {
        Id = id;
        this.jobRole = jobRole;
        this.description = description;
        this.rating = rating;
        this.labourName = labourName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.reviewPostAt = reviewPostAt;
    }
}