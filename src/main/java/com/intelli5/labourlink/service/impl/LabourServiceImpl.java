package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.LabourDTO;
import com.intelli5.labourlink.dto.UpdateLabourDTO;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.service.LabourService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabourServiceImpl implements LabourService {

    @Autowired
    private LabourRepository labourRepository;

    public LabourServiceImpl(LabourRepository labourRepository) {
        this.labourRepository = labourRepository;
    }

    @Override
    public Labour createLabour(Labour labour) {
        return labourRepository.save(labour);
//        Labour savedLabour = labourRepository.save(labour);
//        return savedLabour;
    }

    @Override
    public LabourDTO getLabourById(String email) {
        Labour labour = (Labour) labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Labour is not exist with given id :" + email));




        return convertToLabourDTO(labour);
    }

    @Override
    public List<User> getAllLabour() {
        return labourRepository.findAll();
//        List<User> allLabours = labourRepository.findAll();
//        return allLabours;
    }

    @Override
    @Transactional
    public LabourDTO updateLabour(String email, UpdateLabourDTO updateLabourDTO) {

        Labour existingLabour = (Labour) labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Labour not found for given email: " + email));





        existingLabour.setName(updateLabourDTO.getName());
        existingLabour.setNic(updateLabourDTO.getNic());
//        existingLabour.setDocumentUri(updateLabourDTO.getDocumentUri());
//        existingLabour.setEmail(updateLabourDTO.getEmail());
//        existingLabour.setJobRole(updateLabourDTO.getJobRole());
        existingLabour.setMobileNumber(updateLabourDTO.getMobileNumber());





        Labour updatedLabour = labourRepository.save(existingLabour);
        return convertToLabourDTO(updatedLabour);
    }

    private LabourDTO convertToLabourDTO(Labour labour) {
        LabourDTO labourDTO = new LabourDTO();
        labourDTO.setName(labour.getName());
        labourDTO.setNic(labour.getNic());
        labourDTO.setDocumentUri(labour.getDocumentUri());
        labourDTO.setEmail(labour.getEmail());
        labourDTO.setJobRole(labour.getJobRole());
        labourDTO.setMobileNumber(labour.getMobileNumber());
        return labourDTO;
    }
    @Override
    public void deleteLabour(String email) {
        Labour labour = (Labour) labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Labour not found for given email: " + email));
        labourRepository.delete(labour);
    }

    @Override
    public void updateLabourPassword(String email, String password) {
        Labour labour = (Labour) labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Labour not found for given email: " + email));

        labour.setPassword(password);
        labourRepository.save(labour);
    }

    @Override
    public void updateLabourStatus(String email, Labour updatedLabour) {
        // Fetch the existing labour from the database based on the email
        Labour existingLabour = (Labour) labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for given email: " + email));


        // Update the status of the existing labour with the status from the updated labour
        existingLabour.setStatus(updatedLabour.getStatus());

        // Save the updated labour back to the database
        labourRepository.save(existingLabour);
    }
}