package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.UserRepository;
import com.intelli5.labourlink.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    private final UserRepository customerRepository;
    private final UserRepository labourRepository;

    public UserController(UserService userService, UserRepository customerRepository, UserRepository labourRepository) {
        this.userService = userService;
        this.customerRepository = customerRepository;
        this.labourRepository = labourRepository;
    }


//    @MessageMapping("/user.addUser")
//    @SendTo("/user/public")
//    public User addUser(@Payload User user) {
//        userService.saveUser(user, customerRepository); // Save user to customerRepository
//        return user;
//    }
//
//    @MessageMapping("/user.disconnectUser")
//    @SendTo("/user/public")
//    public User disconnectUser(@Payload User user) {
//        userService.disconnect(user, labourRepository); // Disconnect user from labourRepository
//        return user;
//    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        // Find connected users from both repositories
        List<User> connectedUsers = new ArrayList<>();
        connectedUsers.addAll(userService.findConnectedUsers(customerRepository));
        connectedUsers.addAll(userService.findConnectedUsers(labourRepository));
        return ResponseEntity.ok(connectedUsers);
    }

    @GetMapping("{email}")
    public ResponseEntity<User> getUserById(@PathVariable("email") String email) {
        // Retrieve user from customerRepository
        User userFromCustomerRepo = userService.getUserById(email, customerRepository);

        // Retrieve user from labourRepository
        User userFromLabourRepo = userService.getUserById(email, labourRepository);

        // Return ResponseEntity with the retrieved user
        if (userFromLabourRepo != null) {
            return ResponseEntity.ok(userFromLabourRepo);
        } else {
            return ResponseEntity.ok(userFromCustomerRepo);
        }
    }

}
