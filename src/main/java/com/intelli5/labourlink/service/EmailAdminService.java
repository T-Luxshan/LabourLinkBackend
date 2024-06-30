package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.EmailAdmin;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.EmailAdminRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.repository.UserRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailAdminService {
    @Autowired
    private EmailAdminRepository emailAdminRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    public List<EmailAdmin> findAll() {
        return emailAdminRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
    public EmailAdmin sentEmail(EmailAdmin emails){
        List<User> allLabour=userRepository.findAll();
        for(User user: allLabour){
            EmailAdmin email=new EmailAdmin();
            email.setRecipientEmail(user.getEmail());
            email.setBody(emails.getBody());
            email.setSubject(emails.getSubject());
            email.setSentDate(LocalDate.now());

            //send mail
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(email.getRecipientEmail());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            javaMailSender.send(message);

            emailAdminRepository.save(emails);
        }
        return emails;
    }
}
