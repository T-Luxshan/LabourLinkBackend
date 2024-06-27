package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.EmailAdmin;
import com.intelli5.labourlink.service.EmailAdminService;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin_emails")
public class EmailAdminController {
    @Autowired
    private EmailAdminService emailAdminService;
    @GetMapping
    public ResponseEntity<List<EmailAdmin>> getAll(){
        List<EmailAdmin> emails=emailAdminService.findAll();
       return new ResponseEntity<>(emails, HttpStatus.OK);
    }
}
