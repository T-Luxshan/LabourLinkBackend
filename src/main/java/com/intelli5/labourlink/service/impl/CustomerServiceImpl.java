package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.CustomerDTO;
import com.intelli5.labourlink.dto.UpdateCustomerDTO;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    public CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CustomerDTO getCustomerById(String email) {
        Customer customer = (Customer) customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer is not exist with given id :" + email));

        // Convert Customer to CustomerDTO
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        customerDTO.setStatus(customer.getStatus().name());

        return customerDTO;
    }


    @Override
    public List<User> getAllCustomer() {
        List<User> allCustomers = customerRepository.findAll();
        return allCustomers;
    }

    @Override
    @Transactional
    public Customer updateCustomer(String email, UpdateCustomerDTO updateCustomerDTO) {
        // Fetch the existing customer by email
        Customer existingCustomer = (Customer) customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for given email: " + email));

        System.out.println(existingCustomer.getName());


        // Update the customer details based on the DTO
        existingCustomer.setName(updateCustomerDTO.getName());
        existingCustomer.setAddress(updateCustomerDTO.getAddress());
        existingCustomer.setMobileNumber(updateCustomerDTO.getMobileNumber());

        System.out.println(existingCustomer.getName());

        // Save the updated customer
        return customerRepository.save(existingCustomer);
    }

//    @Override
//    public Customer updateCustomer(String email, Customer updatedCustomer) {
//        Customer existingCustomer = (Customer) customerRepository.findById(email)
//                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for given email: " + email));
//
//        System.out.println(existingCustomer);
//        // Update the existing customer with the provided values
//        existingCustomer.setName(updatedCustomer.getName());
//        existingCustomer.setAddress(updatedCustomer.getAddress());
//        existingCustomer.setMobileNumber(updatedCustomer.getMobileNumber());
//        existingCustomer.setStatus(updatedCustomer.getStatus());
//
//        // Save the updated customer
//        Customer newUpdatedCustomer = customerRepository.save(existingCustomer);
//        return newUpdatedCustomer;
//    }



    @Override
    public void deleteCustomer(String email) {
        Customer customer = (Customer) customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for given email: " + email));
        customerRepository.deleteById(email);
    }


    @Override
    public void updateCustomerPassword(String email, String password) {
        Customer customer = (Customer) customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for given email: " + email));

        customer.setPassword(password);
        customerRepository.save(customer);
    }


    @Override
    public void updateCustomerStatus(String email, Customer updatedCustomer) {
        // Fetch the existing customer from the database based on the email
        Customer existingCustomer = (Customer) customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for given email: " + email));


        // Update the status of the existing customer with the status from the updated customer
        existingCustomer.setStatus(updatedCustomer.getStatus());

        // Save the updated customer back to the database
        customerRepository.save(existingCustomer);
    }
}
