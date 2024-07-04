package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.ResourceNotFoundException;
import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.utils.NotificationHandler;
import com.intelli5.labourlink.dto.NotificationReportDTO;
import com.intelli5.labourlink.dto.ReportDTO;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.entity.UserReport;
import com.intelli5.labourlink.repository.UserReportRepository;
import com.intelli5.labourlink.repository.UserRepository;
import com.intelli5.labourlink.utils.ReportRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserReportService {

    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;
    private final NotificationHandler notificationHandler;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private SseEmitter emitter;
    public SseEmitter createEmitter() {
        this.emitter = new SseEmitter(Long.MAX_VALUE);
        return this.emitter;
    }
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
//        // Notify the admin
        NotificationReportDTO notificationReport=new NotificationReportDTO();
        notificationReport.setTitle(reportRequest.getTitle());
        notificationReport.setReportedToId(reportRequest.getReportedTo());
        notificationReport.setReportedByName(userReport.getReportedBy().getName());
        notificationReport.setId(userReport.getId());
//        notificationReport.setReportedOn(LocalDateTime.now());
//        notificationHandler.sendReportNotification(notificationReport);
//
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
    public List<ReportDTO> getAllReportsForAdmin() {
        List<UserReport> allReports = userReportRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<ReportDTO> reportDTOs = new ArrayList<>();
        for (UserReport report:
                allReports) {
            reportDTOs.add(
                    ReportDTO.builder()
                            .id(report.getId())
                            .title(report.getTitle())
                            .description(report.getDescription())
                            .ReportedByName(report.getReportedBy().getName())
                            .ReportedToName(report.getReportedTo().getEmail())
                            .build()
            );
        }
        return reportDTOs;
    }

    public List<ReportDTO> getReportByEmail(String email) {
        List <UserReport> reports=userReportRepository.findByLabourOrCustomer(email);
        List<ReportDTO> list=new ArrayList<>();
       for(UserReport report : reports){
           ReportDTO reportDTO=new ReportDTO();
           reportDTO.setReportedByName(report.getReportedBy().getName());
           reportDTO.setReportedToName(report.getReportedTo().getName());
           reportDTO.setDescription(report.getDescription());
           list.add(reportDTO);
       }
       return list;
    }

//    public void sendNotification(UserReport notificationReport) {
//        if (this.emitter != null) {
//            try {
//                this.emitter.send(notificationReport);
//            } catch (IOException e) {
//                this.emitter.completeWithError(e);
//            }
//        }
//    }

}