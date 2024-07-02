package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.MailAdminDTO;
import com.intelli5.labourlink.entity.EmailAdmin;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.entity.UserRole;
import com.intelli5.labourlink.repository.EmailAdminRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.repository.UserRepository;
import com.intelli5.labourlink.utils.SuspendMailRequest;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailAdminService {
    @Autowired
    private EmailAdminRepository emailAdminRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;

    public EmailAdmin sentEmail(EmailAdmin emails){
        List<User> allLabour=userRepository.findAll();
        for(User user: allLabour){
            if(user.getRole()== UserRole.CUSTOMER || user.getRole()==UserRole.LABOUR){
            EmailAdmin email=new EmailAdmin();
            email.setRecipientEmail(user.getEmail());
            email.setBody(emails.getBody());
            email.setSubject("Vaccancy");
            email.setSentDate(LocalDate.now());

            //send mail
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(email.getRecipientEmail());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            javaMailSender.send(message);

            emailAdminRepository.save(email);
        }}
        return emails;
    }

    public List<MailAdminDTO> getAllVaccancyMails() {
       List<EmailAdmin> vaccancyMails=emailAdminRepository.findBySubjectOrderByIdDesc("Vaccancy");
        // Group emails by body content
        Map<String, List<EmailAdmin>> groupedByBody = vaccancyMails.stream()
                .filter(mail -> mail.getBody() != null)
                .collect(Collectors.groupingBy(EmailAdmin::getBody));

        // Convert the grouped data to DTOs
        List<MailAdminDTO> mailList = groupedByBody.entrySet().stream()
                .map(entry -> {
           MailAdminDTO mailAdminDTO=new MailAdminDTO();
                    mailAdminDTO.setBody(entry.getKey()); // Set the body
                    mailAdminDTO.setSentDate(entry.getValue().get(0).getSentDate());
                    mailAdminDTO.setRecipientEmail("All");
                    return mailAdminDTO;
       })
                .collect(Collectors.toList());
        return mailList;
    }

    public List<MailAdminDTO> getAllWarningMails() {
        List<EmailAdmin> warningMails=emailAdminRepository.findBySubjectOrderByIdDesc("Warning");
        List<MailAdminDTO> mailList=new ArrayList<>();
        for(EmailAdmin mails:warningMails){
            MailAdminDTO mailAdminDTO=new MailAdminDTO();
            mailAdminDTO.setBody(mails.getBody());
            mailAdminDTO.setSentDate(mails.getSentDate());
            mailAdminDTO.setRecipientEmail(mails.getRecipientEmail());
            mailList.add(mailAdminDTO);
        }
        return mailList;
    }

    public EmailAdmin sentWarningMail(SuspendMailRequest suspendMailRequest) {
            //send mail
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(suspendMailRequest.getRecipientEmail());
            message.setSubject("Warning");
            message.setText(suspendMailRequest.getBody());
            javaMailSender.send(message);


            EmailAdmin emailAdmin = new EmailAdmin();
            emailAdmin.setRecipientEmail(suspendMailRequest.getRecipientEmail());
            emailAdmin.setSubject("Warning");
            emailAdmin.setBody(suspendMailRequest.getBody());
            emailAdmin.setSentDate(LocalDate.now());
            emailAdminRepository.save(emailAdmin);

        return emailAdmin;
    }

    public List<MailAdminDTO> getAllAnniversaryMails() {
        List<EmailAdmin> anniversaryMails=emailAdminRepository.findBySubjectOrderByIdDesc("Anniversary");
        List<MailAdminDTO> mailList=new ArrayList<>();
        for(EmailAdmin mails:anniversaryMails){
            MailAdminDTO mailAdminDTO=new MailAdminDTO();
            mailAdminDTO.setBody(mails.getBody());
            mailAdminDTO.setSentDate(mails.getSentDate());
            mailAdminDTO.setRecipientEmail(mails.getRecipientEmail());
            mailList.add(mailAdminDTO);
        }
        return mailList;
    }
}
