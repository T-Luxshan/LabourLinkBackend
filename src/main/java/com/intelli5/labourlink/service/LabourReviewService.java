package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.ReviewDTO;
import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.LabourReview;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.repository.LabourReviewRepository;
import com.intelli5.labourlink.utils.ReviewRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabourReviewService {

    private final CustomerRepository customerRepository;
    private final LabourRepository labourRepository;
    private final LabourReviewRepository labourReviewRepository;

    public LabourReviewService(CustomerRepository customerRepository,
                               LabourRepository labourRepository,
                               LabourReviewRepository labourReviewRepository) {
        this.customerRepository = customerRepository;
        this.labourRepository = labourRepository;
        this.labourReviewRepository = labourReviewRepository;
    }

    public ReviewDTO addReview(ReviewRequest reviewRequest, String customerEmail) {

        Customer customer = customerRepository.findCustomer(customerEmail);
        Labour labour = labourRepository.findLabour(reviewRequest.getLabourEmail());

        LabourReview review = LabourReview.builder()
                .jobRole(reviewRequest.getJobRole())
//                .workTitle(reviewRequest.getWorkTitle())
                .description(reviewRequest.getDescription())
                .rating(reviewRequest.getRating())
                .customer(customer)
                .labour(labour)
                .build();

        LabourReview labourReview = labourReviewRepository.save(review);

        return ReviewDTO.builder()
                .Id(labourReview.getId())
                .jobRole(labourReview.getJobRole())
//                    .workTitle(labourReview.getWorkTitle())
                .description(labourReview.getDescription())
                .rating(labourReview.getRating())
                .labourName(labourReview.getLabour().getName())
                .customerName(labourReview.getCustomer().getName())
                .build();

//        return "Review added successfully";
    }

    public ResponseEntity<String> deleteReview(Integer id) {

            labourReviewRepository.deleteById(id);
            return ResponseEntity.ok("Review deleted");
    }

    public ReviewDTO getReviewById(Integer id) {
        try {
            LabourReview labourReview = labourReviewRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Review not found exception"));

            return ReviewDTO.builder()
                    .Id(labourReview.getId())
                    .jobRole(labourReview.getJobRole())
//                    .workTitle(labourReview.getWorkTitle())
                    .description(labourReview.getDescription())
                    .rating(labourReview.getRating())
                    .labourName(labourReview.getLabour().getName())
                    .customerName(labourReview.getCustomer().getName())
                    .build();
        } catch (ResourceNotFoundException ignored){
                return new ReviewDTO();
        }
    }

    public ResponseEntity<List<ReviewDTO>> getReviews(String email, String jobRole) {
        Labour labour = labourRepository.findLabour(email);
        List<LabourReview> labourReviews = labourReviewRepository.findAllByEmailAndJobRole(labour, jobRole);

        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (LabourReview review:labourReviews) {
            reviewDTOs.add(
                    ReviewDTO.builder()
                            .Id(review.getId())
                            .jobRole(review.getJobRole())
//                            .workTitle(review.getWorkTitle())
                            .description(review.getDescription())
                            .rating(review.getRating())
                            .labourName(review.getLabour().getName())
                            .customerName(review.getCustomer().getName())
                            .build()
            );

        }
        return ResponseEntity.ok(reviewDTOs);

    }

    public ReviewDTO updateReview(Integer id, ReviewRequest reviewRequest) {
        LabourReview existingReview = labourReviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Review not found exception"));

        existingReview.setJobRole(reviewRequest.getJobRole());
//        existingReview.setWorkTitle(reviewRequest.getWorkTitle());
        existingReview.setDescription(reviewRequest.getDescription());
        existingReview.setRating(reviewRequest.getRating());

        LabourReview updatedReview = labourReviewRepository.save(existingReview);

        return ReviewDTO.builder()
                .Id(updatedReview.getId())
                .jobRole(updatedReview.getJobRole())
//                .workTitle(updatedReview.getWorkTitle())
                .description(updatedReview.getDescription())
                .rating(updatedReview.getRating())
                .labourName(updatedReview.getLabour().getName())
                .customerName(updatedReview.getCustomer().getName())
                .build();
    }

    public List<ReviewDTO> getMyReviews(String email) {
        Labour labour = labourRepository.findLabour(email);
        List<LabourReview> labourReviews = labourReviewRepository.findByLabour(labour);

        List<ReviewDTO> reviewDTOs= new ArrayList<>();
        for (LabourReview review: labourReviews) {
            reviewDTOs.add(
                    ReviewDTO.builder()
                            .Id(review.getId())
                            .jobRole(review.getJobRole())
//                            .workTitle(review.getWorkTitle())
                            .description(review.getDescription())
                            .rating(review.getRating())
                            .labourName(review.getLabour().getName())
                            .customerName(review.getCustomer().getName())
                            .customerEmail(review.getCustomer().getEmail())
                            .build()
            );
        }
        return reviewDTOs;
    }

    public List<ReviewDTO> getAllReviews() {
        List<LabourReview> allReviews = labourReviewRepository.findAll();

        List<ReviewDTO> allReviewDTO = new ArrayList<>();
        for (LabourReview review: allReviews) {
            allReviewDTO.add(
                    ReviewDTO.builder()
                            .Id(review.getId())
                            .jobRole(review.getJobRole())
//                            .workTitle(review.getWorkTitle())
                            .description(review.getDescription())
                            .rating(review.getRating())
                            .labourName(review.getLabour().getName())
                            .customerName(review.getCustomer().getName())
                            .build()
            );
        }
        return allReviewDTO;
    }

    public Double getRating(String email) {
        try {
            Labour labour = labourRepository.findLabour(email);
            return labourReviewRepository.getRating(labour);
        } catch (Exception e){
            return 0.0;
        }
    }
    //------------------------------------------++++++++++++++++++++++++++++++++++++++++++----------------------------
    public List<ReviewDTO> getAllReviewsForAdmin() {
        List<LabourReview> allReviews = labourReviewRepository.findAll(Sort.by(Sort.Direction.DESC, "reviewPostAt"));

        List<ReviewDTO> allReviewDTO = new ArrayList<>();
        for (LabourReview review: allReviews) {
            allReviewDTO.add(
                    ReviewDTO.builder()
                            .Id(review.getId())
                            .jobRole(review.getJobRole())
//                            .workTitle(review.getWorkTitle())
                            .description(review.getDescription())
                            .rating(review.getRating())
                            .labourName(review.getLabour().getName())
                            .customerName(review.getCustomer().getName())
                            .reviewPostAt(LocalDateTime.now())
                            .build()
            );
        }
        return allReviewDTO;
    }
}
