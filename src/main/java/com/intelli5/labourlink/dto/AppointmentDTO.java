package com.intelli5.labourlink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Long id;
    private String customerName;
    private String customerEmail;
    private String labourName;
    private String jobTitle;
    private LocalDate appointmentFixedDate;

}
