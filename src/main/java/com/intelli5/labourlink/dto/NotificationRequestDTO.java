package com.intelli5.labourlink.dto;

import lombok.Data;

@Data
public class NotificationRequestDTO {
    private String title;
    private String message;
    private String recipient;
}
