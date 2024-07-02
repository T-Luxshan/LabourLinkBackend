package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.MailAdminDTO;
import com.intelli5.labourlink.entity.EmailAdmin;
import com.intelli5.labourlink.service.EmailAdminService;
import com.intelli5.labourlink.utils.SuspendMailRequest;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin_emails")
public class EmailAdminController {
    @Autowired
    private EmailAdminService emailAdminService;

    @PostMapping
    public ResponseEntity<EmailAdmin> sentEmail(@RequestBody  EmailAdmin body){
        EmailAdmin response=emailAdminService.sentEmail(body);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<MailAdminDTO>> getAllVaccancyMails(){
        List<MailAdminDTO> emails=emailAdminService.getAllVaccancyMails();
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }

    @PostMapping("/warning")
    public ResponseEntity<EmailAdmin> sentWarningMail(@RequestBody SuspendMailRequest suspendMailRequest){
        EmailAdmin response=emailAdminService.sentWarningMail(suspendMailRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/warning")
    public ResponseEntity<List<MailAdminDTO>> getAllWarningMails(){
        List<MailAdminDTO> emails=emailAdminService.getAllWarningMails();
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }
    @GetMapping("/anniversary")
    public ResponseEntity<List<MailAdminDTO>> getAllAnniversaryMails(){
        List<MailAdminDTO> emails=emailAdminService.getAllAnniversaryMails();
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }
}
