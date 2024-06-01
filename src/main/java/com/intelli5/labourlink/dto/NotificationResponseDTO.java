package com.intelli5.labourlink.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponseDTO {
    private Long id;
    private String title;
    private String message;
    private String recipient;
    private Boolean read;
    private LocalDateTime createdAt;
}
