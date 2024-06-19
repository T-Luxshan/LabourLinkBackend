package com.intelli5.labourlink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationAdminDTO {
    private String name;
    private String email;
    private String documentUri;
    private String jobRole;
    private String joinDate;
}
