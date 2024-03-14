package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    public CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }
    @Override
    public Customer createCustomer(Customer customer){
        Customer savedCustomer=customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public Customer getCustomerById(String email) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return null;
    }

    @Override
    public Customer updateCustomer(String email, Customer customer) {
        return null;
    }

    @Override
    public void deleteEmployee(Long employeeId) {

    }
}
