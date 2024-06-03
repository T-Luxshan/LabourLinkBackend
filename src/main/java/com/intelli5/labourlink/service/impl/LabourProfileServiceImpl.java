//package com.intelli5.labourlink.service.impl;
//
//import com.intelli5.labourlink.entity.LabourProfile;
//import com.intelli5.labourlink.repository.LabourProfileRepository;
//import com.intelli5.labourlink.service.LabourProfileService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class LabourProfileServiceImpl implements LabourProfileService {
//
//    @Autowired
//    private LabourProfileRepository labourProfileRepository;
//
//    @Override
//    public List<LabourProfile> searchByAboutMe(String aboutMe) {
//        return labourProfileRepository.findByAboutMeContaining(aboutMe);
//    }
//
//    @Override
//    public List<LabourProfile> searchByGender(String gender) {
//        return labourProfileRepository.findByGender(gender);
//    }
//    @Override
//    public List<LabourProfile> searchByLanguages(List<String> languages) {
//        return labourProfileRepository.findByLanguagesIn(languages);
//    }
//
//    @Override
//    public List<LabourProfile> searchByLocation(String location) {
//        return labourProfileRepository.findByLocation(location);
//    }
//
//    @Override
//    public LabourProfile createLabourProfile(LabourProfile labourProfile) {
//        return labourProfileRepository.save(labourProfile);
//    }
//
//    @Override
//    public LabourProfile getLabourProfileById(String email) {
//        Optional<LabourProfile> optionalLabourProfile = labourProfileRepository.findById(email);
//        return optionalLabourProfile.orElse(null);
//    }
//
//    @Override
//    public List<LabourProfile> getAllLabourProfile() {
//        return labourProfileRepository.findAll();
//    }
//
//    @Override
//    public LabourProfile updateLabourProfile(String email, LabourProfile updatedLabourProfile) {
//        Optional<LabourProfile> optionalLabourProfile = labourProfileRepository.findById(email);
//        if (optionalLabourProfile.isPresent()) {
//            LabourProfile existingProfile = optionalLabourProfile.get();
//            existingProfile.setAboutMe(updatedLabourProfile.getAboutMe());
//            existingProfile.setGender(updatedLabourProfile.getGender());
//            existingProfile.setLanguages(updatedLabourProfile.getLanguages());
//            existingProfile.setLocation(updatedLabourProfile.getLocation());
//
//            return labourProfileRepository.save(existingProfile);
//
//        }else {
//            return null;
//        }
//    }
//
//    @Override
//    public void deleteLabourProfile(String email) {
//        labourProfileRepository.deleteById(email);
//    }
//
//
//}
