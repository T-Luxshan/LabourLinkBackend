package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.entity.Appointment;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.AppointmentRepo;
import com.intelli5.labourlink.repository.userDetailRepo;
import com.intelli5.labourlink.service.userDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userDetailImpl implements userDetailService {
    @Autowired
    private userDetailRepo userdetailrepo;

@Autowired
private AppointmentRepo appointmentrepo;
    @Override
    public User getUserByEmail(String email) {
        User users=userdetailrepo .findByEmail(email);
        if(users.isPresent())
        return users;
return null;
    }
    public List<User> findAll() {
        Sort sort = Sort.by( Sort.Order.desc("joinDate"),
                Sort.Order.desc("joinTime"));
        return userdetailrepo.findAll(sort);
    }
    public void getUserBy_Email(String email) {
        Optional<User> updateUser= Optional.ofNullable(userdetailrepo.findByEmail(email));
        if (updateUser.isPresent()) {
            User existingUser = updateUser.get();
            existingUser.setPresent(false);
        } else {
            throw new RuntimeException("User not found");
        }

    }

    public List<Appointment> get_UserBy_Email(String email) {
        User users=userdetailrepo .findByEmail(email);
        if(users instanceof Customer){
          return appointmentrepo.findByCustomer((Customer)users);
        } else if (users instanceof Labour){
            return appointmentrepo.findByLabour((Labour) users);
        }else{
            throw new RuntimeException("Invalid"+email);
        }

    }

}
