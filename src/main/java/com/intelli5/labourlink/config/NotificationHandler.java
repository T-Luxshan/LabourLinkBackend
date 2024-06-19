package com.intelli5.labourlink.config;

import com.intelli5.labourlink.dto.NotificationAdminDTO;
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

    public void sendNotification(NotificationAdminDTO notification) {
        logger.info("Sending notification: {}", notification);
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

}
