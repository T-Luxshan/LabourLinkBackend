package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.service.NotificationAdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/adminnotification")
public class NotificationAdminController {
    private final NotificationAdminService notificationAdminService;

    public NotificationAdminController(NotificationAdminService notificationAdminService) {
        this.notificationAdminService = notificationAdminService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationAdmin>> getAllNotifications() {
        return ResponseEntity.ok(notificationAdminService.getAllNotifications());
    }

//    @PostMapping("/send")
//    public void sendNotification(@RequestBody NotificationAdmin userDetail) {
//        notificationAdminService.sendNotification(userDetail);
//    }
//    @GetMapping(value = "/api/adminnotification/sse", produces = "text/event-stream")
//    public SseEmitter streamNotifications() {
//        return notificationAdminService.createEmitter();
//    }


}
