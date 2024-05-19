package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;

import java.util.List;

public interface LabourService {

    Labour createLabour(Labour labour);

    Labour getLabourById(String email);

    List<User> getAllLabour();

    Labour updateLabour(String email, Labour labour);

    void updateLabourPassword(String email, String password);

    void deleteLabour(String email);
}
