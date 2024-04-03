package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Status;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public User getUserById(String email, @Qualifier("customerRepository") UserRepository repository) {
        User user = repository.findById(email).orElseThrow(() -> new ResourceNotFoundException("User does not exist with the given id: " + email));
        return user;
    }


}
