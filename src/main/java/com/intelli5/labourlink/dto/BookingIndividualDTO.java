package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.BookingStage;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.JobRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingIndividualDTO {
    private Customer customer;
    private JobRole jobRole;
    private String jobDescription;
    private LocalDate date;
    private LocalTime startTime;





}
