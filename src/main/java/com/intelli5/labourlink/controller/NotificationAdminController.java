package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.service.NotificationAdminService;
import com.intelli5.labourlink.service.NotificationPollingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adminnotification")
public class NotificationAdminController {
    private final NotificationAdminService notificationAdminService;
    private final NotificationPollingService notificationPollingService;
    public NotificationAdminController(NotificationAdminService notificationAdminService, NotificationPollingService notificationPollingService) {
        this.notificationAdminService = notificationAdminService;
        this.notificationPollingService = notificationPollingService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationAdmin>> getAllNotifications() {
        return ResponseEntity.ok(notificationAdminService.getAllNotifications());
    }

    @GetMapping("/poll")
    public ResponseEntity<List<NotificationAdmin>> getNotifications(@RequestParam(required = false) Long lastCheckedId) {
        List<NotificationAdmin> newNotifications = notificationPollingService.pollForNewNotifications(lastCheckedId);
        return ResponseEntity.ok(newNotifications);
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
