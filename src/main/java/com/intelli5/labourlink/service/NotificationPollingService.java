package com.intelli5.labourlink.service;
import com.intelli5.labourlink.entity.NotificationAdmin;
import com.intelli5.labourlink.entity.UserReport;
import com.intelli5.labourlink.repository.NotificationAdminRepository;
import com.intelli5.labourlink.repository.UserReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
    public List<UserReport> pollForNewUserReport(Integer lastCheckedId) {
        if (lastCheckedId == null) {
            lastCheckedId = 0; // Default to 0 if lastCheckedId is not provided
        }
        return userReportRepositor.findByIdGreaterThan(lastCheckedId);
    }

}
