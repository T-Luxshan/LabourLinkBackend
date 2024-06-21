package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.BookingStage;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.JobRole;
import jdk.jshell.Snippet;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingIndividualDTO {
    private Customer customer;
    private JobRole jobRole;
    private String jobDescription;
    private LocalDate date;
    private LocalTime startTime;

}
