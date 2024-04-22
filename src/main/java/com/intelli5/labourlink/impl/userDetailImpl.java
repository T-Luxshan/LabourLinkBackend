package com.intelli5.labourlink.impl;

import com.intelli5.labourlink.entity.RemovedUserDetail;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.RemovedUserRepo;
import com.intelli5.labourlink.repository.userDetailRepo;
import com.intelli5.labourlink.service.userDetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class userDetailImpl implements userDetailService {
    @Autowired
    private userDetailRepo userdetailrepo;
    @Autowired
    private RemovedUserRepo removedUserRepo;

    @Override
    public User getUserByEmail(String email) {
        return userdetailrepo .findByEmail(email);

    }

    public List<User> findAll() {
        Sort sort = Sort.by( Sort.Order.desc("joinDate"),
                Sort.Order.desc("joinTime"));
        return userdetailrepo.findAll(sort);
    }

    @Transactional
    public void moveDataToArchive(String email) {
        User userData = userdetailrepo.findByEmail(email);
        if (userData != null) {
            // Move data to archived table
            RemovedUserDetail removedUserDetail = new RemovedUserDetail();
            removedUserDetail.setEmail(userData.getEmail());
            // Set other fields as needed

            removedUserRepo.save(removedUserDetail);

            // Delete data from original table
            userdetailrepo.delete(userData);
        } else {
            // Handle case where data for the email is not found
        }
    }

}
