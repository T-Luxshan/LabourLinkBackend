package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.ConnectedUsersDTO;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.UserRepository;
import com.intelli5.labourlink.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Qualifier("customerRepository")
    private final UserRepository customerRepository;
    @Qualifier("labourRepository")
    private final UserRepository labourRepository;


    public UserController(UserService userService, @Qualifier("customerRepository") UserRepository customerRepository, @Qualifier("labourRepository") UserRepository labourRepository) {
        this.userService = userService;
        this.customerRepository = customerRepository;
        this.labourRepository = labourRepository;
    }


    @MessageMapping("/user.add")
    @SendTo("/user/public")
    public User addUser(@Payload User user) {
        if (user instanceof Customer) {
            userService.saveUser(user, customerRepository); // Save user to customerRepository
        } else if (user instanceof Labour) {
            userService.saveUser(user, labourRepository); // Save user to labourRepository
        } else {
            // Handle other user types if needed
        }
        return user;
    }

    @MessageMapping("/user.disconnect")
    @SendTo("/user/public")
    public User disconnectUser(@Payload User user) {
        if (user instanceof Customer) {
            userService.disconnect(user, customerRepository); // Disconnect user from customerRepository
        } else if (user instanceof Labour) {
            userService.disconnect(user, labourRepository); // Disconnect user from labourRepository
        } else {
            // Handle other user types if needed
        }
        return user;
    }


    @GetMapping("/users")
    public ResponseEntity<List<ConnectedUsersDTO>> findConnectedUsers() {
        // Find connected users from both repositories
        List<User> connectedUsers = new ArrayList<>();

        // Retrieve connected users from the customer repository
        List<User> customerUsers = userService.findConnectedUsers(customerRepository);
        connectedUsers.addAll(customerUsers);

        // Retrieve connected users from the labour repository
        List<User> labourUsers = userService.findConnectedUsers(labourRepository);
        for (User labourUser : labourUsers) {
            // Check if the user already exists in the connectedUsers list based on email
            if (!connectedUsers.stream().anyMatch(u -> u.getEmail().equals(labourUser.getEmail()))) {
                connectedUsers.add(labourUser);
            }
        }

        // Convert User entities to ConnectedUsersDTOs
        List<ConnectedUsersDTO> connectedUsersDTOs = connectedUsers.stream()
                .map(user -> ConnectedUsersDTO.builder()
                        .name(user.getName())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(connectedUsersDTOs);
    }


}