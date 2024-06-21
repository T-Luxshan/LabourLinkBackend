package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.utils.JobRoleEnumUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobrole")

public class JobRoleController {
    @GetMapping
    public ResponseEntity<Integer> jobRoleCount(){
        return new ResponseEntity<>(JobRoleEnumUtil.coutJobRole(), HttpStatus.OK);
    }

}
