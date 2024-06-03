//package com.intelli5.labourlink.controller;
//
//import com.intelli5.labourlink.entity.LabourProfile;
//import com.intelli5.labourlink.service.LabourProfileService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/labour-profiles")
//
//public class LabourProfileController {
//    @Autowired
//    private LabourProfileService labourProfileService;
//
//    @PostMapping("/create")
//    public LabourProfile createLabourProfile(@RequestBody LabourProfile labourProfile){
//        return labourProfileService.createLabourProfile(labourProfile);
//    }
//
//    @GetMapping("/all")
//    public List<LabourProfile> getAllLabourProfiles() {
//        return labourProfileService.getAllLabourProfile();
//    }
//
//    @PutMapping("/update/{email}")
//    public LabourProfile updateLabourProfile(@PathVariable String email, @RequestBody LabourProfile labourProfile){
//        return labourProfileService.updateLabourProfile(email, labourProfile);
//    }
//
//    @GetMapping("/get/{email}")
//    public LabourProfile getLabourProfileById(@PathVariable String email){
//        return labourProfileService.getLabourProfileById(email);
//    }
//
//    @DeleteMapping("/delete/{email}")
//    public void deleteLabourProfile(@PathVariable String email) {
//        labourProfileService.deleteLabourProfile(email);
//    }
//
//    @GetMapping("/search/aboutMe")
//    public List<LabourProfile> searchByAboutMe(@RequestParam String aboutMe) {
//        return labourProfileService.searchByAboutMe(aboutMe);
//    }
//
//    @GetMapping("/search/gender")
//    public List<LabourProfile> searchByGender(@RequestParam String gender) {
//        return labourProfileService.searchByGender(gender);
//    }
//
//    @GetMapping("/search/languages")
//    public List<LabourProfile> searchByLanguages(@RequestParam List<String> languages) {
//        return labourProfileService.searchByLanguages(languages);
//    }
//
//    @GetMapping("/search/location")
//    public List<LabourProfile> searchByLocation(@RequestParam String location) {
//        return labourProfileService.searchByLocation(location);
//    }
//
//
//}
