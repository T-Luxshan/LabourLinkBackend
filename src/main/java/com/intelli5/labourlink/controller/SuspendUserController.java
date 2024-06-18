package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.SuspendUserDTO;
import com.intelli5.labourlink.dto.UserDTO;
import com.intelli5.labourlink.entity.SuspendUser;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.service.SuspendUserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@CrossOrigin("*")
@RequestMapping("/api/suspend")
public class SuspendUserController {
    @Autowired
    private SuspendUserService suspendUserService;
    //-------------------User : 02 Table suspend user detail-------------------

    @GetMapping("/all")
    public ResponseEntity<List<SuspendUserDTO>>  getAllUser(){
        List<SuspendUserDTO> users = suspendUserService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    //-------------------User box: 2--------------------------------------------------
    @GetMapping("/count")
    public ResponseEntity<Integer> getAllCusAndLab() {
        int userCount = suspendUserService.getAllCusAndLab();
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }
    //--------------------------------User:-Suspend User detail individual detail fetching -----------------
    @GetMapping("/{email}")
    public ResponseEntity <Optional<SuspendUser>> findByEmail(@PathVariable String email) {
        try{
            Optional<SuspendUser> suspendUser = suspendUserService.getUserByEmail(email);
            if (suspendUser != null) {
                return new ResponseEntity<>(suspendUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
