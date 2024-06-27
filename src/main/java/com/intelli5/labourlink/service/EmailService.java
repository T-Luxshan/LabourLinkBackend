package com.intelli5.labourlink.service;


import com.intelli5.labourlink.dto.MailBody;
import com.intelli5.labourlink.entity.EmailAdmin;
import com.intelli5.labourlink.repository.EmailAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    @Autowired
    private EmailAdminRepository emailAdminRepository;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendSimpleMessage(MailBody mailBody){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setFrom("luxshan.thuraisingam@gmail.com");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());

        javaMailSender.send(message);

    }

    public void sendAnniversaryEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        EmailAdmin emailAdmin=new EmailAdmin();
        emailAdmin.setRecipientEmail(toEmail);
        emailAdmin.setSubject(subject);
        emailAdmin.setBody(body);
        emailAdmin.setSentDate(LocalDate.now());
        emailAdminRepository.save(emailAdmin);

    }
}

