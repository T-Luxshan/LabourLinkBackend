package com.intelli5.labourlink.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}
