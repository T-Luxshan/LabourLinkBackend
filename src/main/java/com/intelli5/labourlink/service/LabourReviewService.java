package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.LabourReview;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.repository.LabourReviewRepository;
import com.intelli5.labourlink.utils.ReviewRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LabourReviewService {

    private final CustomerRepository customerRepository;
    private final LabourRepository labourRepository;
    private final LabourReviewRepository labourReviewRepository;

    public LabourReviewService(@Qualifier("customerRepository") CustomerRepository customerRepository,
                               @Qualifier("labourRepository") LabourRepository labourRepository,
                               LabourReviewRepository labourReviewRepository) {
        this.customerRepository = customerRepository;
        this.labourRepository = labourRepository;
        this.labourReviewRepository = labourReviewRepository;
    }

    public String addReview(ReviewRequest reviewRequest, String customerEmail) {

        Customer customer = customerRepository.findCustomer(customerEmail);
        Labour labour = labourRepository.findLabour(reviewRequest.getLabourEmail());

        LabourReview review = LabourReview.builder()
                .jobRole(reviewRequest.getJobRole())
                .workTitle(reviewRequest.getWorkTitle())
                .description(reviewRequest.getDescription())
                .rating(reviewRequest.getRating())
                .customer(customer)
                .labour(labour)
                .build();

        labourReviewRepository.save(review);

        return "Review added successfully";
    }
}
