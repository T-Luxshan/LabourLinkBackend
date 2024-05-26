package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.service.LabourService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabourServiceImpl implements LabourService {

    private final LabourRepository labourRepository;

    public LabourServiceImpl(LabourRepository labourRepository) {
        this.labourRepository = labourRepository;
    }

    @Override
    public Labour createLabour(Labour labour) {
        return labourRepository.save(labour);
    }

    @Override
    public Labour getLabourById(String email) {
        return (Labour) labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Labour not found for given email: " + email));
    }

    @Override
    public List<User> getAllLabour() {
        return labourRepository.findAll();
    }

    @Override
    public Labour updateLabour(String email, Labour updateLabour) {
        Labour existingLabour = (Labour) labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Labour not found for given email: " + email));

        existingLabour.setName(updateLabour.getName());
        existingLabour.setNic(updateLabour.getNic());
        existingLabour.setMobileNumber(updateLabour.getMobileNumber());
        existingLabour.setStatus(updateLabour.getStatus());

        return labourRepository.save(existingLabour);
    }

    @Override
    public void deleteLabour(String email) {
        labourRepository.deleteById(email);
    }

    @Override
    public void updateLabourPassword(String email, String password) {
        Labour labour = (Labour) labourRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Labour not found for given email: " + email));

        labour.setPassword(password);
        labourRepository.save(labour);
    }
}
