// Package definition for the service layer
package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.LabourCardDTO;
import com.intelli5.labourlink.dto.LabourDTO;

import com.intelli5.labourlink.dto.UpdateLabourDTO;

import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;

import java.util.List;

public interface LabourService {
    // Method to create a new Labour record
    Labour createLabour(Labour labour);

    // Method to get a Labour by email
    LabourDTO getLabourById(String email);

    // Method to retrieve all Labour records
    List<User> getAllLabour();

    // Method to update a specific Labour record
    LabourDTO updateLabour(String email, UpdateLabourDTO updateLabourDTO);

    // Method to update the password of a specific Labour record
    void updateLabourPassword(String email, String password);

    // Method to delete a specific Labour record by email
    void deleteLabour(String email);

    void updateLabourStatus(String email, Labour updatedLabour);

    List<LabourCardDTO> findLabourByJobRole(JobRole jobRole);
}

