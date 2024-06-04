package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.NotificationReadDTO;
import com.intelli5.labourlink.dto.NotificationRequestDTO;
import com.intelli5.labourlink.dto.NotificationResponseDTO;
import com.intelli5.labourlink.entity.Notification;
import com.intelli5.labourlink.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequestDTO request) {
        notificationService.sendNotification(request);
        return ResponseEntity.ok("Notification sent successfully");
    }

    @PostMapping("/sendToAll")
    public ResponseEntity<String> sendNotificationsToAllUsers(@RequestBody NotificationRequestDTO request) {
        notificationService.sendNotificationsToAll(request);
        template.convertAndSend("/topic/notifications", request);
        return ResponseEntity.ok("Notifications sent to all users successfully");
    }

    @PostMapping("/sendToAllCustomers")
    public ResponseEntity<String> sendNotificationsToAllCustomers(@RequestBody NotificationRequestDTO request) {
        notificationService.sendNotificationsToAllCustomers(request);
        return ResponseEntity.ok("Notifications sent to all customers successfully");
    }

    @PostMapping("/sendToAllLabours")
    public ResponseEntity<String> sendNotificationsToAllLabours(@RequestBody NotificationRequestDTO request) {
        notificationService.sendNotificationsToAllLabours(request);
        return ResponseEntity.ok("Notifications sent to all labours successfully");
    }

    @GetMapping("/user/{recipient}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByRecipient(@PathVariable String recipient) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByRecipient(recipient);
        return ResponseEntity.ok(notifications);
    }

    @PatchMapping("/{id}/read")
    public Notification updateNotificationReadStatus(@PathVariable Long id, @RequestBody NotificationReadDTO notificationReadDTO) {
        return notificationService.updateNotificationReadStatus(id, notificationReadDTO.getRead());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }


}
