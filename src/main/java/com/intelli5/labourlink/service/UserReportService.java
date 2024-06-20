package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.config.NotificationHandler;
import com.intelli5.labourlink.dto.NotificationReportDTO;
import com.intelli5.labourlink.dto.ReportDTO;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.entity.UserReport;
import com.intelli5.labourlink.repository.UserReportRepository;
import com.intelli5.labourlink.repository.UserRepository;
import com.intelli5.labourlink.utils.ReportRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserReportService {

    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;
    private final NotificationHandler notificationHandler;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public UserReportService(@Qualifier("customerRepository") UserRepository userRepository, UserReportRepository userReportRepository, NotificationHandler notificationHandler) {

        this.userRepository = userRepository;
        this.userReportRepository = userReportRepository;
        this.notificationHandler = notificationHandler;
    }

    public ReportDTO addReport(String email, ReportRequest reportRequest) {
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
        userReport = userReportRepository.save(userReport);
        // Notify the admin
        NotificationReportDTO notificationReport=new NotificationReportDTO();
        notificationReport.setTitle(userReport.getTitle());
        notificationReport.setReportedToId(userReport.getReportedTo().getEmail());
        notificationReport.setReportedByName(userReport.getReportedBy().getName());

        notificationHandler.sendReportNotification(notificationReport);

        return ReportDTO.builder()
                .id(userReport.getId())
                .title(userReport.getTitle())
                .description(userReport.getDescription())
                .ReportedByName(userReport.getReportedBy().getName())
                .ReportedToName(userReport.getReportedTo().getName())
                .build();

//        return "Report added successfully";
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