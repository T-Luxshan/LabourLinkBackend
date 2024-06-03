package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.dto.ReportDTO;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.entity.UserReport;
import com.intelli5.labourlink.repository.UserReportRepository;
import com.intelli5.labourlink.repository.UserRepository;
import com.intelli5.labourlink.utils.ReportRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public String deleteReport(Integer id) {
        userReportRepository.deleteById(id);
        return "Report deleted successfully";
    }

    public ReportDTO getReportById(Integer id) {
        try{
            UserReport report = userReportRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Report not found"));

            return ReportDTO.builder()
                    .id(report.getId())
                    .title(report.getTitle())
                    .description(report.getDescription())
                    .ReportedByName(report.getReportedBy().getName())
                    .ReportedToName(report.getReportedTo().getName())
                    .build();

        }catch (ResourceNotFoundException resourceNotFoundException){
            return new ReportDTO();
        }
    }

    public ReportDTO updateReport(Integer id, ReportRequest request) {
        try {
            UserReport existingReport = userReportRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Report not found"));
            existingReport.setTitle(request.getTitle());
            existingReport.setDescription(request.getDescription());

            userReportRepository.save(existingReport);
            return ReportDTO.builder()
                    .id(existingReport.getId())
                    .title(existingReport.getTitle())
                    .description(existingReport.getDescription())
                    .ReportedByName(existingReport.getReportedBy().getName())
                    .ReportedToName(existingReport.getReportedTo().getName())
                    .build();
        }
        catch (ResourceNotFoundException resourceNotFoundException){
            return new ReportDTO();
        }

    }

    public List<ReportDTO> getAllReports() {
        List<UserReport> allReports = userReportRepository.findAll();

        List<ReportDTO> reportDTOs = new ArrayList<>();
        for (UserReport report:
             allReports) {
            reportDTOs.add(
                    ReportDTO.builder()
                            .id(report.getId())
                            .title(report.getTitle())
                            .description(report.getDescription())
                            .ReportedByName(report.getReportedBy().getName())
                            .ReportedToName(report.getReportedTo().getName())
                            .build()
            );
        }
        return reportDTOs;
    }
}