package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.service.userDetailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "http://localhost:3001")

public class userDetailController {
    @Autowired
    private userDetailService userdetailservice;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userdetailservice.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{email}")
    @CrossOrigin(origins = "http://localhost:3001")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User user = userdetailservice.getUserByEmail(email);
        return new ResponseEntity<> (user, HttpStatus.NOT_FOUND);
    }
}