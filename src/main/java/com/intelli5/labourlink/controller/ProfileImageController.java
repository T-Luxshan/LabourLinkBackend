package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.ProfileImgDTO;
import com.intelli5.labourlink.service.ProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profile")
public class ProfileImageController {


    @Autowired
    private final ProfileImageService profileImageService;

    public ProfileImageController(ProfileImageService profileImageService) {
        this.profileImageService = profileImageService;
    }

    @PostMapping()
    public ResponseEntity<ProfileImgDTO> addProfilePhoto(@RequestBody ProfileImgDTO profileImgDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return ResponseEntity.ok(profileImageService.addProfilePhoto(currentPrincipalName, profileImgDTO));
    }

    @PutMapping()
    public ResponseEntity<ProfileImgDTO> updateProfilePhotoUser(@RequestBody ProfileImgDTO profileImgDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return ResponseEntity.ok(profileImageService.updateProfilePhotoByUser(currentPrincipalName, profileImgDTO));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteProfileByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return ResponseEntity.ok(profileImageService.deleteProfile(currentPrincipalName));
    }

    @GetMapping()
    public ResponseEntity<ProfileImgDTO> getProfilePhotoByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return ResponseEntity.ok(profileImageService.getProfile(currentPrincipalName));
    }
}
