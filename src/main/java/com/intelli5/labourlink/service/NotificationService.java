package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.NotificationRequestDTO;
import com.intelli5.labourlink.dto.NotificationResponseDTO;
import com.intelli5.labourlink.entity.Notification;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.NotificationRepository;
import com.intelli5.labourlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    public void sendNotification(NotificationRequestDTO request) {
        // Check if the recipient exists in the database
        System.out.println(request.getRecipient());

        User recipient = userService.getUserById(request.getRecipient());

        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setRecipient(request.getRecipient());

        System.out.println(notification);

        // Save notification to the database
        notificationRepository.save(notification);
    }

    public void sendNotificationsToAll(NotificationRequestDTO request) {
        // Get all users
        List<User> users = userService.getAllUsers();


        // Send notification to each user
        for (User user : users) {
            Notification notification = new Notification();
            notification.setTitle(request.getTitle());
            notification.setMessage(request.getMessage());
            notification.setRecipient(user.getEmail());

            notificationRepository.save(notification);
        }
    }

    // Method to send notifications to all customers
    public void sendNotificationsToAllCustomers(NotificationRequestDTO request) {
        List<User> customers = userService.getAllCustomers();

        for (User customer : customers) {
            Notification notification = new Notification();
            notification.setTitle(request.getTitle());
            notification.setMessage(request.getMessage());
            notification.setRecipient(customer.getEmail()); // Assuming User has getEmail() method

            notificationRepository.save(notification);
        }
    }

    // Method to send notifications to all labours
    public void sendNotificationsToAllLabours(NotificationRequestDTO request) {
        List<User> labours = userService.getAllLabors();

        for (User labour : labours) {
            Notification notification = new Notification();
            notification.setTitle(request.getTitle());
            notification.setMessage(request.getMessage());
            notification.setRecipient(labour.getEmail()); // Assuming User has getEmail() method

            notificationRepository.save(notification);
        }
    }

    public List<NotificationResponseDTO> getNotificationsByRecipient(String recipient) {
        return notificationRepository.findByRecipient(recipient)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    private NotificationResponseDTO convertToResponseDTO(Notification notification) {
        NotificationResponseDTO responseDTO = new NotificationResponseDTO();
        responseDTO.setId(notification.getId());
        responseDTO.setTitle(notification.getTitle());
        responseDTO.setMessage(notification.getMessage());
        responseDTO.setRecipient(notification.getRecipient());
        responseDTO.setCreatedAt(notification.getCreatedAt());
        responseDTO.setRead(notification.getRead());
        return responseDTO;
    }

    public Notification updateNotificationReadStatus(Long id, Boolean read) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setRead(read);
            return notificationRepository.save(notification);
        }
        throw new RuntimeException("Notification not found with id " + id);
    }
}
