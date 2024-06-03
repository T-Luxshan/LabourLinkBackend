package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.entity.UserReport;
import com.intelli5.labourlink.repository.UserReportRepository;
import com.intelli5.labourlink.repository.UserRepository;
import com.intelli5.labourlink.utils.ReportRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserReportService {

    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;

    public UserReportService(@Qualifier("customerRepository") UserRepository userRepository, UserReportRepository userReportRepository) {

        this.userRepository = userRepository;
        this.userReportRepository = userReportRepository;
    }

    public String addReview(String email, ReportRequest reportRequest) {
        User reportedByUser = userRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        User reportedToUser = userRepository.findById(reportRequest.getReportedTo())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        UserReport userReport = UserReport.builder()
                .title(reportRequest.getTitle())
                .description(reportRequest.getDescription())
                .ReportedBy(reportedByUser)
                .ReportedTo(reportedToUser)
                .build();
        userReportRepository.save(userReport);

        return "Report added successfully";
    }
}