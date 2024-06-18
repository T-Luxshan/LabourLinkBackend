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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailsDTO {
    private BookingStage bookingStage;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private JobRole jobRole;
    private String customerId;
    private String labourId;
    private String customerName;
    private String labourName;
}
