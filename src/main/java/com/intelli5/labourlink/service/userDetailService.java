package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.User;

import java.util.List;

public interface userDetailService {
     User getUserByEmail(String email) ;


    List<User> findAll();
}
