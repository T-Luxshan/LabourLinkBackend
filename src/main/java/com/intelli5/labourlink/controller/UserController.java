package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.*;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final LabourRepository labourRepository;

    public UserController(UserService userService, @Qualifier("customerRepository") CustomerRepository customerRepository, @Qualifier("labourRepository") LabourRepository labourRepository) {
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


//    @GetMapping("/connectedCustomers")
//    public ResponseEntity<List<ConnectedUsersDTO>> findConnectedCustomers() {
//        // Retrieve connected users from the customer repository
//        List<User> customerUsers = userService.findConnectedCustomers();
//
//        // Convert User entities to ConnectedUsersDTOs
//        List<ConnectedUsersDTO> connectedUsersDTOs = customerUsers.stream()
//                .map(user -> ConnectedUsersDTO.builder()
//                        .name(user.getName())
//                        .email(user.getEmail())
//                        .mobileNumber(user.getMobileNumber())
//                        .status(user.getStatus())
//                        .build())
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(connectedUsersDTOs);
//    }

//    @GetMapping("/connectedLabours")
//    public ResponseEntity<List<ConnectedUsersDTO>> findConnectedLabours() {
//        // Retrieve connected users from the labor repository
//        List<User> laborUsers = userService.findConnectedLabours();
//
//        // Convert User entities to ConnectedUsersDTOs
//        List<ConnectedUsersDTO> connectedUsersDTOs = laborUsers.stream()
//                .map(user -> ConnectedUsersDTO.builder()
//                        .name(user.getName())
//                        .email(user.getEmail())
//                        .mobileNumber(user.getMobileNumber())
//                        .status(user.getStatus())
//                        .build())
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(connectedUsersDTOs);
//    }

    @GetMapping("/connectedUsers/{senderId}")
    public ResponseEntity<List<ConnectedUsersDTO>> findConnectedUsers(@PathVariable String senderId) {
        // Retrieve connected users from the labor repository
        List<User> laborUsers = userService.findConnectedUsers(senderId);

        // Convert User entities to ConnectedUsersDTOs
        List<ConnectedUsersDTO> connectedUsersDTOs = laborUsers.stream()
                .map(user -> ConnectedUsersDTO.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .mobileNumber(user.getMobileNumber())
                        .status(user.getStatus())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(connectedUsersDTOs);
    }


    @GetMapping("{email}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("email") String email) {
        try {
            // Retrieve user from customerRepository via userService
            User userFromCustomerRepo = userService.getCustomerById(email);

            // Retrieve user from laborRepository via userService
            User userFromLaborRepo = userService.getLaborById(email);

            // Create a UserDTO object from the retrieved user
            UserDTO userDTO;
            if (userFromLaborRepo != null) {
                userDTO = convertToDTO(userFromLaborRepo);
            } else {
                userDTO = convertToDTO(userFromCustomerRepo);
            }

            // Return ResponseEntity with the created UserDTO
            return ResponseEntity.ok(userDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{email}")
    public ResponseEntity<UserDTO> updateUserStatus(@PathVariable("email") String email, @RequestBody UserStatusUpdateDTO updateUserStatusDTO) {
        try {
            // Update user in customer repository
            User updatedCustomer = userService.updateCustomer(email, updateUserStatusDTO);
            return ResponseEntity.ok(convertToDTO(updatedCustomer));
        } catch (ResourceNotFoundException e1) {
            try {
                // Update user in labor repository
                User updatedLabor = userService.updateLabor(email, updateUserStatusDTO);
                return ResponseEntity.ok(convertToDTO(updatedLabor));
            } catch (ResourceNotFoundException e2) {
                return ResponseEntity.notFound().build();
            }
        }
    }

    // Helper method to convert User to UserDTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getName(), user.getEmail(), user.getMobileNumber(), user.getStatus());
    }


    //+++++++++++++++++++++++++-----------------------------------------------------------------------+++++++++++++++++++++++++++++++
    //****-------------------User : 01 Table All user detail----------------------------------------
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<UserDTO> users = userService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    //***-------------------Dashboard box: 1 ......and .......User detail box : 1-------------------
    @GetMapping("/count")
    public ResponseEntity<Integer> getAllUserCount() {
        int userCount = userService.getAllUserCount();
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

    //***--------------------------------User:-User detail individual detail fetching -----------------
    @GetMapping("u/{email}")
    public ResponseEntity <Optional<UserAdminDTO>> findUserByEmail(@PathVariable String email) {
        try{
            Optional<UserAdminDTO> user = userService.findUserByEmail(email);
            if (user.isPresent()) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //***--------------------------------User:-User detail individual detail Remove -----------------
    @PutMapping ("/u/{email}")
    public ResponseEntity<Void> removeUserByEmail(@PathVariable String email ,@RequestBody Map<String, String> request) {
        String removalPurpose = request.get("removalPurpose");
        try {
            userService.removeUserByEmail(email,removalPurpose);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }}


    //--------------------------------User:-03 table Deactivate User detail------------------------------------
    @GetMapping("/deactivate/all")
    public ResponseEntity<List<UserDTO>>  getDeactivatedUser(){
        List<UserDTO> users = userService.getDeactivatedUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    //-------------------Dashboard box: 1 ......and .......User detail box : 1-------------------
    @GetMapping("/deactivate/count")
    public ResponseEntity<Integer> deactivateCount() {
        int userCount = userService.getDeactivateCount();
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }
    //--------------------------------User:-Deactivate User detail individual detail  -----------------
    @GetMapping("/deactivate/{email}")
    public ResponseEntity <Optional<User>>findDeactivateUserByEmail(@PathVariable String email) {
        try{
            Optional<User> user = userService.findDeactivateUserByEmail(email);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //--------------------------------Profile name fetching ---------------------------------
    @GetMapping("/userProfile")
    public ResponseEntity<AdminProfileDTO> fetchProfileName (){
        // Get the authenticated user's email from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userService.fetchProfileName(currentPrincipalName)// Fetch the user details using the email
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + currentPrincipalName));
        AdminProfileDTO userProfile = new AdminProfileDTO(user.getName(), user.getEmail());

        return ResponseEntity.ok(userProfile);
    }


    @GetMapping("/user")
    public ResponseEntity<GetUserEmailFromTokenDTO> getUserByToken() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        GetUserEmailFromTokenDTO email = userService.getUserByEmail(currentPrincipalName);
        return ResponseEntity.ok(email);
    }

}//-------------------------Adding deactivate user : My purpose -----------------------------
//    @PutMapping ("/deactivate/{email}")
//    public ResponseEntity<Void> DeactivatedUser(@PathVariable String email ) {
//        try {
//            userService.getUserBy_Email_(email);
//            return ResponseEntity.ok().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }}