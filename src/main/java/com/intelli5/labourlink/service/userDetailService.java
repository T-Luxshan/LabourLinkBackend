package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.Appointment;
import com.intelli5.labourlink.entity.User;

import java.util.List;

public interface userDetailService {
    User getUserByEmail(String email) ;
    void getUserBy_Email(String email) ;
    List<User> findAll();

    List<Appointment> get_UserBy_Email(String email) ;
}
