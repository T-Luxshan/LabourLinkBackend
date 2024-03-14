package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    //Build Add Employee REST API
    @PostMapping
    public ResponseEntity<Customer> createEmployee(@RequestBody Customer customer){
        Customer savedEmployee=customerService.createCustomer(customer);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }
}
