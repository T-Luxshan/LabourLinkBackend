package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.UserStatusUpdateDTO;
import com.intelli5.labourlink.entity.*;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LabourRepository labourRepository;

    public void saveUser(User user, CustomerRepository customerRepository) {
        user.setStatus(Status.ONLINE);
        customerRepository.save(user);
    }

    public void saveUser(User user, LabourRepository labourRepository) {
        user.setStatus(Status.ONLINE);
        labourRepository.save(user);
    }

    public void disconnect(User user, @Qualifier("customerRepository") UserRepository repository) {
        User storedUser = repository.findById(user.getEmail()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    public List<User> findConnectedCustomers() {
        return customerRepository.findAllByStatusAndRole(Status.ONLINE, UserRole.CUSTOMER);
    }

    public List<User> findConnectedLabours() {
        return labourRepository.findAllByStatusAndRole(Status.ONLINE,UserRole.LABOUR);
    }

    public User getCustomerById(String email) {
        // Try to find the user in the customer repository
        Optional<User> customerUserOptional = customerRepository.findById(email);
        if (customerUserOptional.isPresent()) {
            return customerUserOptional.get();
        }
        // If the user is not found in the customer repository, return null
        return null;
    }

    public User getLaborById(String email) {
        // Try to find the user in the labor repository
        Optional<User> laborUserOptional = labourRepository.findById(email);
        if (laborUserOptional.isPresent()) {
            return laborUserOptional.get();
        }
        // If the user is not found in the labor repository, return null
        return null;
    }

    public User getUserById(String id) {
        User user = getCustomerById(id);
        if (user == null) {
            user = getLaborById(id);
        }
        return user;
    }


    public User updateCustomer(String email, UserStatusUpdateDTO updateUserStatusDTO) {
        Optional<User> optionalUser = Optional.ofNullable(getUserById(email));
        User existingUser = optionalUser
                .orElseThrow(() -> new ResourceNotFoundException("User not found for given email: " + email));

        existingUser.setStatus(updateUserStatusDTO.getStatus());
        return customerRepository.save(existingUser);
    }

    public User updateLabor(String email, UserStatusUpdateDTO updateUserStatusDTO) {
        User existingUser = labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for given email: " + email));

        existingUser.setStatus(updateUserStatusDTO.getStatus());
        return labourRepository.save(existingUser);
    }




    public User updateUser(String email, User updateUser) {
        User existingUser = customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for given email: " + email));

        existingUser.setStatus(updateUser.getStatus());
        return customerRepository.save(existingUser);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(getAllCustomers());
        allUsers.addAll(getAllLabors());
        return allUsers;
    }

    public List<User> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<User> getAllLabors() {
        return labourRepository.findAll();
    }

}
