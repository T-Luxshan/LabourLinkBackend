package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.service.userDetailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/user")
@RestController
@CrossOrigin("http://localhost:3000/user-detail")
public class userDetailController {
    @Autowired
    private userDetailService userdetailservice;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userdetailservice.findAll(), HttpStatus.OK);

    }
}