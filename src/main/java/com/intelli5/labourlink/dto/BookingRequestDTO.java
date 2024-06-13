package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.BookingStage;
import com.intelli5.labourlink.entity.JobRole;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingRequestDTO {

    private String labourId;
    private String customerId;
    private LocalDate date;
    private LocalTime startTime;
    private BookingStage bookingStage;
    private String jobDescription;
    private JobRole jobRole;
}