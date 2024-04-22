// Package definition for the service layer
package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;

import java.util.List;

public interface LabourService {
    // Method to create a new Labour record
    Labour createLabour(Labour labour);

    // Method to get a Labour by email
    Labour getLabourById(String email);

    // Method to retrieve all Labour records
    List<User> getAllLabour();

    // Method to update a specific Labour record
    Labour updateLabour(String email, Labour labour);

    // Method to update the password of a specific Labour record
    void updateLabourPassword(String email, String password);

    // Method to delete a specific Labour record by email
    void deleteLabour(String email);
}
