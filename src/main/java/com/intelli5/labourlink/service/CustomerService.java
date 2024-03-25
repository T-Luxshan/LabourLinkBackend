package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomerById(String email);

    List<Customer> getAllCustomer();

    Customer updateCustomer(String email,Customer customer);

    void updateCustomerPassword(String email,String password);

    void deleteCustomer(String email);
}
