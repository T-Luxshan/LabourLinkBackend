package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.GetUserEmailFromTokenDTO;
import com.intelli5.labourlink.dto.UserStatusUpdateDTO;
import com.intelli5.labourlink.entity.*;
import com.intelli5.labourlink.repository.AppointmentRepository;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Qualifier("customerRepository")
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LabourRepository labourRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private UserRepository userRepository;


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
        return labourRepository.findAllByStatusAndRole(Status.ONLINE, UserRole.LABOUR);
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

    public GetUserEmailFromTokenDTO getUserByEmail(String currentPrincipalName) {
        User user = getLaborById(currentPrincipalName);
        return GetUserEmailFromTokenDTO.builder()
                .email(user.getEmail())
                .build();
    }


    //------------------------------------------------------------------------------------------------------
    public Optional<User> getUserByEmailId(String email) {
        Optional<User> users = labourRepository.findByEmail(email);
        if (users.isPresent())
            return users;
        return null;
    }

    public List<User> findAll() {
        Sort sort = Sort.by(Sort.Order.desc("joinDate"),
                Sort.Order.desc("joinTime"));
        return labourRepository.findAll(sort);
    }

    public void getUserBy_Email(String email) {
        Optional<User> updateUser = labourRepository.findByEmail(email);
        if (updateUser.isPresent()) {
            User existingUser = updateUser.get();
            existingUser.setPresent(false);
        } else {
            throw new RuntimeException("User not found");
        }

    }

    public List<Appointment> get_UserBy_Email(String email) {
        Optional<User> optionaluser = labourRepository.findByEmail(email);
        if (optionaluser.isPresent()) {
            User user = optionaluser.get();
            if (user instanceof Customer) {
                return appointmentRepository.findByCustomer((Customer) user);
            } else if (user instanceof Labour) {
                return appointmentRepository.findByLabour((Labour) user);
            } else {
                throw new RuntimeException("Invalid" + email);
            }
        } else {
            throw new RuntimeException("not found" + email);
        }
    }
}



