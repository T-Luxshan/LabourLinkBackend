package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.LabourProfileDTO;
import com.intelli5.labourlink.entity.LabourProfile;
import com.intelli5.labourlink.service.LabourProfileService;
import com.intelli5.labourlink.utils.LabourProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labour-profiles")

public class LabourProfileController {
    @Autowired
    private LabourProfileService labourProfileService;



    @PostMapping("/create")
    public ResponseEntity<LabourProfileDTO> createLabourProfile(@RequestBody LabourProfileRequest labourProfileRequest){
        LabourProfileDTO savedLabourProfile = labourProfileService.createLabourProfile(labourProfileRequest);
        return new ResponseEntity<>(savedLabourProfile, HttpStatus.CREATED);
    }



    @GetMapping("/getLabourProfileById/{email}")
    public ResponseEntity<LabourProfileDTO> getLabourProfileById(@PathVariable String email){
        LabourProfileDTO labourProfile = labourProfileService.getLabourProfileById(email);
        return ResponseEntity.ok(labourProfile);
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<LabourProfileDTO> updateLabourProfile(@PathVariable String email, @RequestBody LabourProfileRequest labourProfileRequest){
        LabourProfileDTO updatedLabourProfile = labourProfileService.updateLabourProfile(email, labourProfileRequest);
        return ResponseEntity.ok(updatedLabourProfile);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteLabourProfile(@PathVariable String email) {
        labourProfileService.deleteLabourProfile(email);
        return ResponseEntity.ok("Labour profile deleted successfully");
    }

    @GetMapping("/search/aboutMe")
    public ResponseEntity<List<LabourProfile>> searchByAboutMe(@RequestParam String aboutMe) {
        List<LabourProfile> profiles = labourProfileService.searchByAboutMe(aboutMe);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/search/gender")
    public ResponseEntity<List<LabourProfile>> searchByGender(@RequestParam String gender) {
        List<LabourProfile> profiles = labourProfileService.searchByGender(gender);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/search/languages")
    public ResponseEntity<List<LabourProfile>> searchByLanguages(@RequestParam List<String> languages) {
        List<LabourProfile> profiles = labourProfileService.searchByLanguages(languages);
        return ResponseEntity.ok(profiles);
    }


}
