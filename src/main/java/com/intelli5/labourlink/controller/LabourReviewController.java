package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.ReviewDTO;
import com.intelli5.labourlink.service.LabourReviewService;
import com.intelli5.labourlink.utils.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while adding the review.");
        }
    }

    @DeleteMapping("/deleteReviewById/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer id){
        return labourReviewService.deleteReview(id);
    }

    @GetMapping("/getReviewById/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Integer id){
        return ResponseEntity.ok(labourReviewService.getReviewById(id));

    }

    @GetMapping("/getReviews/{email}/{jobRole}")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable String email,
                                                      @PathVariable String jobRole){
        return labourReviewService.getReviews(email, jobRole);
    }

    @PutMapping("/editReview/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Integer id,
                                                  @RequestBody ReviewRequest reviewRequest){
        return ResponseEntity.ok(labourReviewService.updateReview(id, reviewRequest));
    }

    @GetMapping("/getMyReviews/{email}")
    public ResponseEntity<List<ReviewDTO>> getMyReviews(@PathVariable String email){
        return ResponseEntity.ok(labourReviewService.getMyReviews(email));
    }

    @GetMapping("/getAllReview")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(){
        return ResponseEntity.ok(labourReviewService.getAllReviews());
    }
}
