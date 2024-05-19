package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.Appointment;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.service.userDetailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class userDetailController {
    @Autowired
    private userDetailService userdetailservice;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userdetailservice.findAll(), HttpStatus.OK);
    }
  //-------------------Dashboard box: 1 ......and .......User detail box : 1-------------------
    @GetMapping("/count")
    public ResponseEntity<Integer> getAllUserCount() {
        List<User> userList = userdetailservice.findAll();
        int userCount = userList.size();
        return new ResponseEntity <>(userCount, HttpStatus.OK);
    }
//--------------------------------User:-User detail individual detail fetching -----------------
    @GetMapping("/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User user = userdetailservice.getUserByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//--------------------------------User:-User detail individual detail Remove -----------------
@PutMapping ("/{email}")
public ResponseEntity<Void> findBy_Email(@PathVariable String email) {
        try{
             userdetailservice.getUserBy_Email(email);
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }catch(RuntimeException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
    //--------------------------------User:-User detail individual appointment detail fetching -----------------
    @GetMapping("/appointment/{email}")
    public ResponseEntity<List<Appointment>> find_By_Email(@PathVariable String email) {
        try{
            List<Appointment> appointments = userdetailservice.get_UserBy_Email(email);
            return new ResponseEntity<>(appointments,HttpStatus.OK);
        }catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}