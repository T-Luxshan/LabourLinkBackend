package com.intelli5.labourlink.service;
import com.intelli5.labourlink.dto.ReportDTO;
import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.entity.UserReport;
import com.intelli5.labourlink.repository.NotificationAdminRepository;
import com.intelli5.labourlink.repository.UserReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationPollingService {
    @Autowired
    private NotificationAdminRepository notificationAdminRepository;
    @Autowired
    private UserReportRepository userReportRepositor;

    public List<NotificationAdmin> pollForNewNotifications(Long lastCheckedId) {
        return notificationAdminRepository.findByIdGreaterThan(lastCheckedId);
    }
    public List<ReportDTO> pollForNewUserReport(Integer lastCheckedReportedId) {

        List<UserReport> list= userReportRepositor.findByIdGreaterThan(lastCheckedReportedId);

        List<ReportDTO> reportDTOs = new ArrayList<>();
        for (UserReport report:list) {
            reportDTOs.add(
                    ReportDTO.builder()
                            .id(report.getId())
                            .title(report.getTitle())
                            .ReportedByName(report.getReportedBy().getName())
                            .ReportedToName(report.getReportedTo().getEmail())
                            .build()
            );
        }
        return reportDTOs;
    }

}
