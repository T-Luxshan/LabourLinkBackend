package com.intelli5.labourlink.dto;

import lombok.Builder;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private String senderId;
    private String recipientId;
    private String message;
}
