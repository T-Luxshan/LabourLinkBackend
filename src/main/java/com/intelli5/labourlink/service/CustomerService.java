package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.UserId;
import com.intelli5.labourlink.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomerById(UserId userId);

    List<Customer> getAllCustomer();

    Customer updateCustomer(UserId userId,Customer customer);

    void deleteEmployee(Long employeeId);
}
