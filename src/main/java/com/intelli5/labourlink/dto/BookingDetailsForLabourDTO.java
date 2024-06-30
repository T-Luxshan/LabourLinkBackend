package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.BookingStage;
import com.intelli5.labourlink.entity.JobRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class BookingDetailsForLabourDTO {
    private Long id;
    private JobRole jobRole;
    private String customerName;
    private LocalDate date;
    private LocalTime startTime;
    private BookingStage bookingStage;
    private String jobDescription;
    private String customerEmail;
    private String labourName;

}
