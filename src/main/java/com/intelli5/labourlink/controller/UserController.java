package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
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
    public ResponseEntity<List<User>> findConnectedUsers() {
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

        return ResponseEntity.ok(connectedUsers);
    }


//    @GetMapping("{email}")
//    public ResponseEntity<User> getUserById(@PathVariable("email") String email) {
//        // Retrieve user from customerRepository
//        User userFromCustomerRepo = userService.getUserById(email, customerRepository);
//
//        // Retrieve user from labourRepository
//        User userFromLabourRepo = userService.getUserById(email, labourRepository);
//
//        // Return ResponseEntity with the retrieved user
//        if (userFromLabourRepo != null) {
//            return ResponseEntity.ok(userFromLabourRepo);
//        } else {
//            return ResponseEntity.ok(userFromCustomerRepo);
//        }
//    }

    @GetMapping("{email}")
    public ResponseEntity<User> getUserById(@PathVariable("email") String email) {
        try {
            // Retrieve user from customerRepository via userService
            User userFromCustomerRepo = userService.getCustomerById(email);

            // Retrieve user from laborRepository via userService
            User userFromLaborRepo = userService.getLaborById(email);

            // Return ResponseEntity with the retrieved user
            // If user is found in labor repository, prioritize it
            if (userFromLaborRepo != null) {
                return ResponseEntity.ok(userFromLaborRepo);
            } else {
                return ResponseEntity.ok(userFromCustomerRepo);
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


//    @PutMapping("{email}")
//    public ResponseEntity<User> updateUser(@PathVariable("email") String email, @RequestBody User updatedUser) {
//        if (updatedUser instanceof Customer) {
//            User updatedCustomer = userService.updateUser(email, updatedUser, customerRepository);
//            return ResponseEntity.ok(updatedCustomer);
//        } else if (updatedUser instanceof Labour) {
//            User updatedLabour = userService.updateUser(email, updatedUser, labourRepository);
//            return ResponseEntity.ok(updatedLabour);
//        } else {
//            // Handle other user types if needed
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @PutMapping("{email}")
    public ResponseEntity<User> updateUserStatus(@PathVariable("email") String email, @RequestBody User updateUser) {
        try {
            // Update user in customer repository
            User updatedCustomer = userService.updateCustomer(email, updateUser);
            return ResponseEntity.ok(updatedCustomer);
        } catch (ResourceNotFoundException e1) {
            try {
                // Update user in labor repository
                User updatedLabor = userService.updateLabor(email, updateUser);
                return ResponseEntity.ok(updatedLabor);
            } catch (ResourceNotFoundException e2) {
                return ResponseEntity.notFound().build();
            }
        }
    }

}
