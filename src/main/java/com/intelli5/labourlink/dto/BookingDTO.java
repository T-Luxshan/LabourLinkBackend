package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.JobRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String labourName;
    private JobRole jobRole;
    private LocalDate bookingMadeDate;
}
