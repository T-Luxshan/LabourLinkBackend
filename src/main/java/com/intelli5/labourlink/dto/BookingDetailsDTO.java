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
    private Long id;
    private BookingStage bookingStage;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private JobRole jobRole;
    private String customerId;
    private String labourId;
    private String customerName;
    private String labourName;
    private float amount;
    private String description;

    public BookingDetailsDTO(BookingStage bookingStage, LocalDate appointmentDate, LocalTime appointmentTime, JobRole jobRole, String customerId, String labourId, String customerName, String labourName) {
        this.bookingStage = bookingStage;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.jobRole = jobRole;
        this.customerId = customerId;
        this.labourId = labourId;
        this.customerName = customerName;
        this.labourName = labourName;
    }

    public BookingDetailsDTO(float amount) {
        this.amount = amount;
    }
}
