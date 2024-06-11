package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.CustomerDTO;
import com.intelli5.labourlink.dto.PasswordDTO;

import com.intelli5.labourlink.dto.UpdateCustomerDTO;

import com.intelli5.labourlink.dto.UserDTO;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createEmployee(@RequestBody Customer customer){
        Customer savedEmployee=customerService.createCustomer(customer);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //Build Get Customer REST API
//    @GetMapping("/user")
//    public ResponseEntity<Customer> getCustomerByToken(){
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//
//        Customer customer = customerService.getCustomerById(currentPrincipalName);
//        return ResponseEntity.ok(customer);

//        try {
//            Customer customer = customerService.getCustomerById(currentPrincipalName);
//            return ResponseEntity.ok(customer);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching customer details: " + e.getMessage());
//        }
//    }

    @GetMapping("{email}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String email) {
        CustomerDTO customerDTO = customerService.getCustomerById(email);
        return ResponseEntity.ok(customerDTO);
    }



    //Build Get Customer REST API

//    @GetMapping()
//    public ResponseEntity<List<UserDTO>> getAllCustomer(){
//        List<User> AllCustomers=customerService.getAllCustomer();
//
//        List<UserDTO> userDTOs = new ArrayList<>();
//        for (User user : AllCustomers) {
//            UserDTO userDTO = UserDTO.builder()
//                    .email(user.getEmail())
//                    .name(user.getName())
//                    .mobileNumber(user.getMobileNumber())
//                    .role(String.valueOf(user.getRole()))
//                    .build();
//            userDTOs.add(userDTO);
//        }
//        return ResponseEntity.ok(userDTOs);
//    }

    @PutMapping("{email}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("email") String email, @RequestBody UpdateCustomerDTO updatedCustomerDTO){
        Customer updatedCustomer = customerService.updateCustomer(email, updatedCustomerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

//    @PutMapping("{email}")
//    public ResponseEntity<Customer> updateCustomer(@PathVariable("email") String email, @RequestBody Customer updatedCustomer){
//        Customer customer = customerService.updateCustomer(email, updatedCustomer);
//        return ResponseEntity.ok(customer);
//    }

    //Build Put Customer REST API to updatePassword
    @PutMapping("/changePassword/{email}")
    public ResponseEntity<String> updateCustomerPassword(@PathVariable("email") String email, @RequestBody PasswordDTO password){
        customerService.updateCustomerPassword(email, password.getNewPassword());
        return ResponseEntity.ok("Customer Password Updated successfully");
    }

    @DeleteMapping("/deleteCustomer/{email}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("email") String email){
        customerService.deleteCustomer(email);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
