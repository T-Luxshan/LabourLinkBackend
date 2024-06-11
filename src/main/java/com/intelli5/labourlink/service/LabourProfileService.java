package com.intelli5.labourlink.service;


import com.intelli5.labourlink.dto.LabourProfileDTO;
import com.intelli5.labourlink.entity.LabourProfile;
import com.intelli5.labourlink.utils.LabourProfileRequest;


import java.util.List;

public interface LabourProfileService {

List<LabourProfile> searchByAboutMe(String aboutMe);
List<LabourProfile> searchByGender(String gender);
List<LabourProfile> searchByLanguages(List<String> languages);
//List<LabourProfile> searchByLocation(String location);


//LabourProfile createLabourProfile(LabourProfile labourProfile);
LabourProfileDTO createLabourProfile(LabourProfileRequest labourProfileRequest);
LabourProfileDTO getLabourProfileById(String email);
List<LabourProfile> getAllLabourProfile();
LabourProfile updateLabourProfile(String email, LabourProfileRequest labourProfileRequest);
void deleteLabourProfile(String email);

}
