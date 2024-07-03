package com.intelli5.labourlink.service;

import com.intelli5.labourlink.utils.NotificationHandler;
import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.repository.NotificationAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service

public class NotificationAdminService {
    private final NotificationHandler notificationHandler;
    @Autowired
    private final NotificationAdminRepository notificationAdminRepository;
    private SseEmitter emitter;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public SseEmitter createEmitter() {
        this.emitter = new SseEmitter(Long.MAX_VALUE);
        return this.emitter;
    }
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
        return notificationAdminRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

//    public void sendNotification(NotificationAdmin userDetail) {
//        if (this.emitter != null) {
//            try {
//                this.emitter.send(userDetail);
//            } catch (IOException e) {
//                this.emitter.completeWithError(e);
//            }
//        }
//    }
}
