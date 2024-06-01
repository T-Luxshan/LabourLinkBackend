package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.service.LabourReviewService;
import com.intelli5.labourlink.utils.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/labourReview")
public class LabourReviewController {

    @Autowired
    LabourReviewService labourReviewService;
    @PostMapping("/addReview")
    public ResponseEntity<String> addReview(@RequestBody ReviewRequest reviewRequest){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            return ResponseEntity.ok(labourReviewService.addReview(reviewRequest, currentPrincipalName));
//            return ResponseEntity.ok("Review added successfully");
        } catch (Exception e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }




    }
}
