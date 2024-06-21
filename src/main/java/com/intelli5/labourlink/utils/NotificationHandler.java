package com.intelli5.labourlink.utils;

import com.intelli5.labourlink.dto.NotificationReportDTO;
import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.entity.UserReport;
import com.intelli5.labourlink.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationHandler {
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(NotificationAdmin notification) {
        logger.info("Sending notification: {}", notification);
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    public void sendReportNotification(NotificationReportDTO userReport) {
        logger.info("Sending reports: {}", userReport);
        messagingTemplate.convertAndSend("/topic/reports", userReport);
    }
}
