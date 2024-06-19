package com.intelli5.labourlink.service;

import com.google.firebase.messaging.Notification;
import com.intelli5.labourlink.config.NotificationHandler;
import com.intelli5.labourlink.dto.NotificationAdminDTO;
import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.repository.NotificationAdminRepository;
import com.intelli5.labourlink.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NotificationAdminService {
    private final NotificationHandler notificationHandler;
    @Autowired
    private final NotificationAdminRepository notificationAdminRepository;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationAdminService(NotificationHandler notificationHandler, NotificationAdminRepository notificationAdminRepository) {
        this.notificationHandler = notificationHandler;
        this.notificationAdminRepository = notificationAdminRepository;
    }
    //--------------------Register Labour notification ----------------------------------------------
    public void notifyAdmin(NotificationAdmin userDetail) {
        logger.info("Sending notification: {}", userDetail);
        NotificationAdmin savedNotification=notificationAdminRepository.save(userDetail);
        notificationHandler.sendNotification(savedNotification);

    }

    public List<NotificationAdmin>getAllNotifications() {
        return notificationAdminRepository.findAll(Sort.by(Sort.Order.asc("joinDate")));
    }
}
