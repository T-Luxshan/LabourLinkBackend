package com.intelli5.labourlink.utils;

import com.intelli5.labourlink.entity.EmailAdmin;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.EmailAdminRepository;
import com.intelli5.labourlink.repository.UserRepository;
import com.intelli5.labourlink.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class AnniversaryScheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    @Scheduled(cron = "0 0 0 * * ?") // This runs daily at midnight
    public void sendAnniversaryEmails() {
        List<com.intelli5.labourlink.entity.User> users = userRepository.findAll();

        LocalDate today = LocalDate.now();

        for (User user : users) {
            LocalDate joinDate = user.getJoinDate();
            long yearsSinceJoining = ChronoUnit.YEARS.between(joinDate, today);

            // Check if the years since joining is a whole number and greater than 0
            if (yearsSinceJoining > 0 && yearsSinceJoining == Math.floor(yearsSinceJoining)) {
                emailService.sendAnniversaryEmail(user.getEmail(), "Anniversary", "Happy Anniversary to Us !"+ user.getName() + ",\n\nCongratulations on your anniversary with us! Thank you for being a part of our company.\n\nBest regards,\n-Labour Link-");


            }
        }
    }
}
