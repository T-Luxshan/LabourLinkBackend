package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.Status;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Qualifier("customerRepository")
    @Autowired
    private UserRepository customerRepository;

    @Qualifier("labourRepository")
    @Autowired
    private UserRepository labourRepository;

    public void saveUser(User user, @Qualifier("customerRepository") UserRepository repository) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
    }

    public void disconnect(User user, @Qualifier("customerRepository") UserRepository repository) {
        User storedUser = repository.findById(user.getEmail()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    public List<User> findConnectedUsers(@Qualifier("customerRepository") UserRepository repository) {
        return repository.findAllByStatus(Status.ONLINE);
    }


//    public User getUserById(String email, @Qualifier("customerRepository") UserRepository repository) {
//        User user = repository.findById(email).orElseThrow(() -> new ResourceNotFoundException("User does not exist with the given id: " + email));
//        return user;
//    }
//
//    public User getUserById(String email, @Qualifier("customerRepository") UserRepository customerRepository, @Qualifier("laborRepository") UserRepository laborRepository) {
//        // Try to find the user in the customer repository
//        Optional<User> customerUserOptional = customerRepository.findById(email);
//        if (customerUserOptional.isPresent()) {
//            return customerUserOptional.get();
//        }
//
//        // Try to find the user in the labor repository
//        Optional<User> laborUserOptional = laborRepository.findById(email);
//        if (laborUserOptional.isPresent()) {
//            return laborUserOptional.get();
//        }
//
//        // If the user is not found in either repository, throw an exception
//        throw new ResourceNotFoundException("User does not exist with the given id: " + email);
//    }

    public User getCustomerById(String email) {
        // Try to find the user in the customer repository
        Optional<User> customerUserOptional = customerRepository.findById(email);
        if (customerUserOptional.isPresent()) {
            return customerUserOptional.get();
        }

        // If the user is not found in the customer repository, throw an exception
        throw new ResourceNotFoundException("User does not exist with the given id: " + email);
    }

    public User getLaborById(String email) {
        // Try to find the user in the labor repository
        Optional<User> laborUserOptional = labourRepository.findById(email);
        if (laborUserOptional.isPresent()) {
            return laborUserOptional.get();
        }

        // If the user is not found in the labor repository, throw an exception
        throw new ResourceNotFoundException("User does not exist with the given id: " + email);
    }



//    public User updateUser(String email, User updateUser, UserRepository repository) {
//        User existingUser = repository.findById(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found for given email: " + email));
//
//        // Check the type of updateUser and update accordingly
//        if (updateUser instanceof Customer) {
//            Customer updatedCustomer = (Customer) updateUser;
//            // Cast updateUser to Customer and update fields accordingly
//            existingUser.setStatus(updatedCustomer.getStatus());
//        } else if (updateUser instanceof Labour) {
//            Labour updatedLabour = (Labour) updateUser;
//            // Cast updateUser to Labour and update fields accordingly
//            // Update Labour-specific fields if any
//            existingUser.setStatus(updatedLabour.getStatus());
//        }
//
//        User newUpdatedUser = repository.save(existingUser);
//        return newUpdatedUser;
//    }

    public User updateCustomer(String email, User updateUser) {
        User existingUser = customerRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for given email: " + email));

        existingUser.setStatus(updateUser.getStatus());
        return customerRepository.save(existingUser);
    }

    public User updateLabor(String email, User updateUser) {
        User existingUser = labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for given email: " + email));

        existingUser.setStatus(updateUser.getStatus());
        return labourRepository.save(existingUser);
    }





}
