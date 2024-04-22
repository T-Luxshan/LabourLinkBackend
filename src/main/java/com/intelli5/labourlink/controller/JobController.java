package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.entity.Job;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.service.JobService;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/job")
public class JobController {
@Autowired
private JobService jobservice;
    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobservice.createJob(job);
        return new ResponseEntity<>("Job Add", HttpStatus.CREATED );
    }
    @GetMapping("/count")
    public ResponseEntity<Integer> getAllJobCount() {
       List<Job> JobCount=jobservice.findAll();
        int count=JobCount.size();
       return new ResponseEntity<>(count,HttpStatus.OK);
    }
    /*
  @GetMapping("/countwithname")
  List<Object[]>  getLabourJobCountsWithId(){
        return jobservice.getLabourJobCountsWithId();
  }*/
    //...............................dashboard - pie chart -------------------------------
    /*
    @GetMapping("/dashboard/g_demandjob")
    public ResponseEntity<List<Object[]>> getJobDemandData() {
        List<Object[]> jobDemandData = jobservice.demandJobDetail();
        return new ResponseEntity<>(jobDemandData, HttpStatus.OK);
    }*/


}
