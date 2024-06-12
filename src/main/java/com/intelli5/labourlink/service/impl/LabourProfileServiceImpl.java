package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.LabourProfileDTO;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.LabourProfile;
import com.intelli5.labourlink.repository.LabourProfileRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.service.LabourProfileService;
import com.intelli5.labourlink.utils.LabourProfileRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabourProfileServiceImpl implements LabourProfileService {
    private final LabourProfileRepository labourProfileRepository;
    private final LabourRepository labourRepository;

    @Autowired
    public LabourProfileServiceImpl(LabourProfileRepository labourProfileRepository, LabourRepository labourRepository)  {
        this.labourProfileRepository = labourProfileRepository;
        this.labourRepository = labourRepository;


    }




    @Override
    public List<LabourProfile> searchByAboutMe(String aboutMe) {
        return labourProfileRepository.findByAboutMeContaining(aboutMe);
    }

    @Override
    public List<LabourProfile> searchByGender(String gender) {
        return labourProfileRepository.findByGender(gender);
    }
    @Override
    public List<LabourProfile> searchByLanguages(List<String> languages) {
        return labourProfileRepository.findByLanguagesIn(languages);
    }

//    @Override
//    public List<LabourProfile> searchByLocation(String location) {
//        return labourProfileRepository.findByLocation(location);
//    }



    @Override
    @Transactional
    public LabourProfileDTO createLabourProfile(LabourProfileRequest labourProfileRequest) {

      Labour labour = labourRepository.findLabour(labourProfileRequest.getLabourEmail());

      LabourProfile profile = LabourProfile.builder()
              .aboutMe(labourProfileRequest.getAboutMe())
              .gender(labourProfileRequest.getGender())
              .languages(labourProfileRequest.getLanguages())
//              .location(labourProfileRequest.getLocation())
              .labour(labour)
                      .build();

      LabourProfile labourProfile = labourProfileRepository.save(profile);

      return LabourProfileDTO.builder()
              .aboutMe(labourProfile.getAboutMe())
              .gender(labourProfile.getGender())
              .languages(labourProfile.getLanguages())
//              .location(labourProfile.getLocation())
              .build();

    }

    @Override
    public LabourProfileDTO getLabourProfileById(String email) {
       try {
           Labour labour = labourRepository.findLabour(email);
           LabourProfile labourProfile = (LabourProfile) labourProfileRepository.findByLabour(labour)
                   .orElseThrow(() -> new ResourceNotFoundException("Labour not found exception"));

           return LabourProfileDTO.builder()
                   .aboutMe(labourProfile.getAboutMe())
                   .gender(labourProfile.getGender())
                   .languages(labourProfile.getLanguages())
//                   .location(labourProfile.getLocation())
                   .build();


       }
       catch (ResourceNotFoundException resourceNotFoundException){
           return  LabourProfileDTO.builder().build();

       }
    }

    @Override
    public List<LabourProfile> getAllLabourProfile() {
        return labourProfileRepository.findAll();
    }

    @Override
    public LabourProfileDTO updateLabourProfile(String email, LabourProfileRequest labourProfileRequest) {

        try{
            Labour labour = labourRepository.findLabour(email);
            LabourProfile existingProfile = (LabourProfile) labourProfileRepository.findByLabour(labour)
                    .orElseThrow(()-> new ResourceNotFoundException("Profile not found"));


            existingProfile.setAboutMe(labourProfileRequest.getAboutMe().isBlank() ? existingProfile.getAboutMe() : labourProfileRequest.getAboutMe());
            existingProfile.setGender(labourProfileRequest.getGender().isBlank() ? existingProfile.getGender() : labourProfileRequest.getGender());
            existingProfile.setLanguages(labourProfileRequest.getLanguages().toArray().length == 0 ? existingProfile.getLanguages() : labourProfileRequest.getLanguages());
//            existingProfile.setLocation(labourProfileRequest.getLocation());

            LabourProfile updatedProfile = labourProfileRepository.save(existingProfile);

            return LabourProfileDTO.builder()
                    .aboutMe(updatedProfile.getAboutMe())
                    .gender(updatedProfile.getGender())
                    .languages(updatedProfile.getLanguages())
//                   .location(labourProfile.getLocation())
                    .build();

        }catch (ResourceNotFoundException resourceNotFoundException){
            return null;
        }
    }

    @Override
    public void deleteLabourProfile(String email) {
        Labour labour = labourRepository.findLabour(email);
        labourProfileRepository.deleteByLabour(labour);
    }


}
