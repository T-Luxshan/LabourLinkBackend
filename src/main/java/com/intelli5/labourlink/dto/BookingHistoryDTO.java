package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.BookingStage;
import com.intelli5.labourlink.entity.JobRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingHistoryDTO {

    private Long id;
    private String labourName;
    private JobRole jobRole;
    private LocalDate date;
    private LocalTime startTime;
    private BookingStage bookingStage;
    private String jobDescription;
    private String labourId;

    public BookingHistoryDTO(Long id, String labourName, JobRole jobRole, LocalDate date, LocalTime startTime, BookingStage bookingStage, String jobDescription) {
        this.id = id;
        this.labourName = labourName;
        this.jobRole = jobRole;
        this.date = date;
        this.startTime = startTime;
        this.bookingStage = bookingStage;
        this.jobDescription = jobDescription;
    }
}
