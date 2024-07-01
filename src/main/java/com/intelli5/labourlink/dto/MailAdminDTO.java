package com.intelli5.labourlink.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MailAdminDTO {
    private Long Id;
    private String recipientEmail;
    private String subject;
    private String body;
    private LocalDate sentDate;

}
