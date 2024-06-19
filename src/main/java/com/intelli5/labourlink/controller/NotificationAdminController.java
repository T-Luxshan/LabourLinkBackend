package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.service.NotificationAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
