package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.LabourCardDTO;
import com.intelli5.labourlink.dto.LabourDTO;
import com.intelli5.labourlink.dto.LabourNewlyVerifiedDTO;
import com.intelli5.labourlink.dto.MailBody;
import com.intelli5.labourlink.dto.LabourLocationDTO;
import com.intelli5.labourlink.dto.UpdateLabourDTO;
import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.LabourLocations;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.service.EmailService;
import com.intelli5.labourlink.service.LabourReviewService;
import com.intelli5.labourlink.service.LabourService;
import com.intelli5.labourlink.service.ProfileImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class LabourServiceImpl implements LabourService {

    @Autowired
    private LabourRepository labourRepository;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private LabourReviewService labourReviewService;

    @Autowired
    private ProfileImageService profileImageService;

    public LabourServiceImpl(LabourRepository labourRepository, EmailService emailService) {

        this.labourRepository = labourRepository;
        this.emailService = emailService;
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
    @Override
    public List<LabourCardDTO> findLabourByJobRole(JobRole jobRole) {
        List<Labour> allLabours = labourRepository.findLabourByJobRole(jobRole);

        return allLabours.stream()
                .map(labour -> {
                    LabourCardDTO dto = new LabourCardDTO();
                    dto.setLabourName(labour.getName());
                    dto.setJobRole(labour.getJobRole());
                    dto.setRating(labourReviewService.getRating(labour.getEmail()));
                    dto.setLabourEmail((labour.getEmail()));
                    dto.setProfileUri(profileImageService.getProfile(labour.getEmail()).getProfileUri());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    //--------------+++++++++++++++++++++++++++++++++++++++++++++++++------------------------------------
    @Transactional
    public LabourNewlyVerifiedDTO getLabourByIdForVerification(String email){
        Optional<User> optionalLabour = labourRepository.findById(email);
        if (optionalLabour.isPresent()) {
            User labour = optionalLabour.get();
            optionalLabour.get().setVerified(true);
            labourRepository.save(labour);

            LabourNewlyVerifiedDTO labourNewlyVerifiedDtos=new LabourNewlyVerifiedDTO();
            labourNewlyVerifiedDtos.setName(labour.getName());
            labourNewlyVerifiedDtos.setEmail(labour.getEmail());
            // Send verification email
            MailBody mailBody = MailBody.builder()
                    .to(email)
                    .text("Your account has been successfully verified.Now you connected with us.Enjoy your journey, Our team will Contact you via Phone call for the further inquiries  -Labour Link-")
                    .subject("Account Verification")
                    .build();
            emailService.sendSimpleMessage(mailBody);
            return labourNewlyVerifiedDtos;
        } else {
            throw new ResourceNotFoundException("Labour not found with email: " + email);
        }
    }

    @Override
    public Boolean isLabourVerified(String email) {
        Labour optionalLabour =labourRepository.findLabour(email);
        return optionalLabour.isVerified();
    }
    public Map<JobRole,Integer> countLaboursByJobRole(){
       List<Object[]> results=labourRepository.countLaboursByJobRole();
        Map<JobRole, Integer> jobRoleCounts = new HashMap<>();
        for (Object[] result : results) {
            JobRole jobRole = JobRole.valueOf((String) result[0]);
            if (jobRole != null) {
                Long countLong = ((Number) result[1]).longValue(); // Cast to Number and get long value
                Integer count = countLong != null ? countLong.intValue() : 0;
                jobRoleCounts.put(jobRole, count);
            }
        }
        return jobRoleCounts;
    }



}