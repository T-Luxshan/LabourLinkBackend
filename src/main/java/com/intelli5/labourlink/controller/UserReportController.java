package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.ReportDTO;
import com.intelli5.labourlink.service.UserReportService;
import com.intelli5.labourlink.utils.ReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report")
public class UserReportController {

    @Autowired
    UserReportService userReportService;

    @PostMapping("/user")
    public ResponseEntity<String> reportUser(@RequestBody ReportRequest reportRequest){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            return ResponseEntity.ok(userReportService.addReview(currentPrincipalName, reportRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while adding the review.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Integer id){
        return ResponseEntity.ok(userReportService.deleteReport(id));
    }

    @GetMapping("/getReportById/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Integer id){
        return ResponseEntity.ok(userReportService.getReportById(id));
    }
}
