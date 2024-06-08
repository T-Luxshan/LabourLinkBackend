package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.BookingStage;
import com.intelli5.labourlink.entity.JobRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class BookingHistoryDTO {

    private Long id;
    private String labourName;
    private JobRole jobRole;
    private LocalDate date;
    private LocalTime startTime;
    private BookingStage bookingStage;
    private String jobDescription;
}
