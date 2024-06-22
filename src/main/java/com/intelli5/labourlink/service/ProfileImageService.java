package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.ProfileImgDTO;
import com.intelli5.labourlink.entity.ProfileImage;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.ProfileImageRepository;
import com.intelli5.labourlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProfileImageService {

    @Autowired
    private final ProfileImageRepository profileImageRepository;

    private final UserRepository userRepository;
    public ProfileImageService(ProfileImageRepository profileImageRepository, @Qualifier("customerRepository") UserRepository userRepository) {
        this.profileImageRepository = profileImageRepository;
        this.userRepository = userRepository;
    }

    public ProfileImgDTO addProfilePhoto(String email, ProfileImgDTO profileImgDTO) {
        User user = userRepository.findById(email)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        ProfileImage profileImage = ProfileImage.builder()
                .profileUri(profileImgDTO.getProfileUri())
                .user(user)
                .build();

        try{
            profileImage = profileImageRepository.save(profileImage);

            return ProfileImgDTO.builder()
                    .profileUri(profileImage.getProfileUri())
                    .build();
        } catch (Exception e) {
            return updateProfilePhotoByUser(email, profileImgDTO);
        }

    }

    public ProfileImgDTO updateProfilePhotoByUser(String email, ProfileImgDTO profileImgDTO) {
        User user = userRepository.findById(email)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        ProfileImage existingProfile = profileImageRepository.findByUser(user);
       existingProfile.setProfileUri(profileImgDTO.getProfileUri());
        existingProfile = profileImageRepository.save(existingProfile);

        return ProfileImgDTO.builder()
                .profileUri(existingProfile.getProfileUri())
                .build();
    }

    public String deleteProfile(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        profileImageRepository.deleteByUser(user);

        return "Deletion success";
    }

    public ProfileImgDTO getProfile(String currentPrincipalName) {
        User user =  userRepository.findById(currentPrincipalName)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        try{
            ProfileImage profileImage = profileImageRepository.findByUser(user);
            return ProfileImgDTO.builder()
                    .profileUri(profileImage.getProfileUri())
                    .build();
        }
        catch (NullPointerException nullPointerException){
            return new ProfileImgDTO();
        }
    }
}
