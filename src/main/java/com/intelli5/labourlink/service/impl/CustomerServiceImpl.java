package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.User;
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
        Customer customer= (Customer) customerRepository.findById(email).orElseThrow(() -> new ResourceNotFoundException("Customer is exit with give id :" + email));
        return customer;
    }

    @Override
    public List<User> getAllCustomer() {
        List<User> allCustomers=customerRepository.findAll();
        return allCustomers;
    }

    @Override
    public Customer updateCustomer(String email, Customer updateCustomer) {
        Customer existingCustomer = (Customer) customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for given email: " + email));

        existingCustomer.setName(updateCustomer.getName());
        existingCustomer.setAddress(updateCustomer.getAddress());
        existingCustomer.setMobileNumber(updateCustomer.getMobileNumber());

        Customer newUpdatedCustomer=customerRepository.save(existingCustomer);
        return newUpdatedCustomer;
    }

    @Override
    public void deleteCustomer(String email) {
        Customer customer = (Customer) customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for given email: " + email));
        customerRepository.deleteById(email);
    }

    @Override
    public void updateCustomerPassword(String email, String password){
        Customer customer = (Customer) customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for given email: " + email));

        customer.setPassword(password);
        customerRepository.save(customer);
    }
}
