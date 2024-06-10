package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.CustomerDTO;
import com.intelli5.labourlink.dto.UpdateCustomerDTO;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.User;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    CustomerDTO getCustomerById(String email);


    List<User> getAllCustomer();

//    Customer updateCustomer(String email,Customer customer);

    void updateCustomerPassword(String email,String password);

    Customer updateCustomer(String email, UpdateCustomerDTO updateCustomerDTO);

    void deleteCustomer(String email);

    void updateCustomerStatus(String email, Customer updatedCustomer);
}
