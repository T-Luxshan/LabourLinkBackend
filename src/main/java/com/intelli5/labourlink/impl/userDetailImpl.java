package com.intelli5.labourlink.impl;

import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.userDetailRepo;
import com.intelli5.labourlink.service.userDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class userDetailImpl implements userDetailService {
    @Autowired
    private userDetailRepo userdetailrepo;
    public List<User> findAll() {
        return userdetailrepo.findAll();
    }

}
