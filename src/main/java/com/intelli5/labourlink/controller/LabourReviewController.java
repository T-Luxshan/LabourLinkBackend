package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.BookingIndividualDTO;
import com.intelli5.labourlink.dto.LabourReviewIndividualDTO;
import com.intelli5.labourlink.dto.ReviewDTO;
import com.intelli5.labourlink.service.LabourReviewService;
import com.intelli5.labourlink.utils.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/labourReview")
public class LabourReviewController {

    @Autowired
    LabourReviewService labourReviewService;
    @PostMapping("/addReview")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewRequest reviewRequest){

//        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            return ResponseEntity.ok(labourReviewService.addReview(reviewRequest, currentPrincipalName));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("An error occurred while adding the review.");
//        }
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

    @GetMapping("/getMyReviews")
    public ResponseEntity<List<ReviewDTO>> getMyReviews(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            return ResponseEntity.ok(labourReviewService.getMyReviews(currentPrincipalName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/getAllReview")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(){
        return ResponseEntity.ok(labourReviewService.getAllReviews());
    }

    @GetMapping("/rating")
    public ResponseEntity<Double> getRating(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            return ResponseEntity.ok(labourReviewService.getRating(currentPrincipalName));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(-1.0);
        }
    }

    @GetMapping("/rating/{email}")
    public ResponseEntity<Double> getRating(@PathVariable String email){
            return ResponseEntity.ok(labourReviewService.getRating(email));
    }
    //---------------------------++++++++++++++++++++++++++++++++++++++++++++----------------------------------------

    //***-------------------Review : Fetching review with in a particular time gap  -------------------------------------------------
    @GetMapping("/getAllReviewForAdmin")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsForAdmin(){
        return ResponseEntity.ok(labourReviewService.getAllReviewsForAdmin());
    }
    //****-------------------Review :Delete review by id  -------------------------------------------------
    @DeleteMapping("/deleteByAdmin/{id}")
    public ResponseEntity<Void> deleteReviewByAdmin(@PathVariable Integer id){
        labourReviewService.deleteReviewByAdmin(id);
        return ResponseEntity.noContent().build();
    }


//***------------------------------User:-User detail individual Review detail fetching -----------------
    @GetMapping("/review/{email}")
    public ResponseEntity<List<LabourReviewIndividualDTO>> getReviewAdmin(@PathVariable String email) {
            List<LabourReviewIndividualDTO> reviews = labourReviewService.getReviewAdmin(email);
            if (reviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
    }

