package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.SuspendUserDTO;
import com.intelli5.labourlink.entity.SuspendUser;
import com.intelli5.labourlink.repository.SuspendUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SuspendUserService {
@Autowired
private SuspendUserRepository suspendUserRepository;
    public List<SuspendUserDTO> getAllUser() {
            List <SuspendUserDTO> suspendUserDtos=new ArrayList<>();
           // List<SuspendUser> suspendUsers=suspendUserRepository.findAll(Sort.by(Sort.Order.desc("joinDate"), Sort.Order.desc("joinTime")));
            List<SuspendUser> suspendUsers=suspendUserRepository.findAll();
            for(SuspendUser suspendUser:suspendUsers) {
                SuspendUserDTO suspendUserDto = new SuspendUserDTO();
                suspendUserDto.setName(suspendUser.getName());
                suspendUserDto.setEmail(suspendUser.getEmail());
                suspendUserDto.setJoinDate(suspendUser.getJoinDate());
                suspendUserDto.setRole(suspendUser.getRole());
                suspendUserDtos.add(suspendUserDto);
                }
            return suspendUserDtos;
        }
    public int getAllCusAndLab() {
        return getAllUser().size();

    }
    public Optional<SuspendUser> getUserByEmail(String email) {
        return suspendUserRepository .findByEmail(email);
    }

    public Optional<SuspendUser> findByEmail(String email) {
        return suspendUserRepository .findByEmail(email);
    }
}

