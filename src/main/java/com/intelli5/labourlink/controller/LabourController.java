package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.LabourCardDTO;
import com.intelli5.labourlink.dto.LabourDTO;
import com.intelli5.labourlink.dto.PasswordDTO;
import com.intelli5.labourlink.dto.UpdateLabourDTO;
import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.service.LabourService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/labour")
public class LabourController {

    private final LabourService labourService;
    private final PasswordEncoder passwordEncoder;

    //Build Add Labour REST API//
    @PostMapping("/createLabour")
    public ResponseEntity<Labour> createLabour(@RequestBody Labour labour){
        Labour savedLabour=labourService.createLabour(labour);
        return new ResponseEntity<>(savedLabour, HttpStatus.CREATED);
    }

    //Build Get Labour REST API
    @GetMapping("/getLabourById/{email}")
    public ResponseEntity<LabourDTO> getLabourById(@PathVariable String email){
        LabourDTO labourDTO=labourService.getLabourById(email);
        return ResponseEntity.ok(labourDTO);
    }



    @PutMapping("{email}")
    public ResponseEntity<LabourDTO> updateLabour(@PathVariable("email") String email, @RequestBody UpdateLabourDTO updatedLabourDTO){
        LabourDTO updatedLabour = labourService.updateLabour(email, updatedLabourDTO);
        return ResponseEntity.ok(updatedLabour);
    }


    //Build Put Labour REST API to updatePassword
    @PutMapping("/changePassword/{email}")
    public ResponseEntity<String> updateLabourPassword(@PathVariable("email") String email, @RequestBody PasswordDTO password){
        String encodePassword = passwordEncoder.encode(password.getNewPassword());
        labourService.updateLabourPassword(email, encodePassword);
        return ResponseEntity.ok("Labour Password Updated successfully");
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteLabour(@PathVariable("email") String email){
        labourService.deleteLabour(email);
        return ResponseEntity.ok("Labour deleted successfully");
    }
    @GetMapping("/getLabourByJobRole/{jobRole}")
    public List<LabourCardDTO> getLabourByJobRole(@PathVariable JobRole jobRole){
        return labourService.findLabourByJobRole(jobRole);

    }

}
