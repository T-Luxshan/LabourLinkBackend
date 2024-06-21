//package com.intelli5.labourlink.controller;
//
//import com.intelli5.labourlink.entity.Job;
//import com.intelli5.labourlink.service.JobService;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@CrossOrigin("*")
//@RestController
//@RequestMapping("/job")
//public class JobController {
//@Autowired
//private JobService jobservice;
////-----------------------------------Job :- add job---------------------------------------
//    @PostMapping
//    public ResponseEntity<String> createJob(@RequestBody Job job){
//       if(jobservice.existsByJobName(job.getJobName())){
//           return ResponseEntity.status(HttpStatus.CONFLICT)//conflict 409
//                   .body("job"+job.getJobName() + "is already exists");
//       }
//        else {
//           jobservice.createJob(job);
//           return new ResponseEntity<>("Job Add successfully", HttpStatus.CREATED);
//       }}
//    //---------------------------Dashboard & -Job :-  box 01 - job count -------------------------------
//    @GetMapping("/count")
//    public ResponseEntity<Integer> getAllJobCount() {
//       List<Job> JobCount=jobservice.findAll();
//        int count=JobCount.size();
//       return new ResponseEntity<>(count,HttpStatus.OK);
//    }
//    /*
//  @GetMapping("/countwithname")
//  List<Object[]>  getLabourJobCountsWithId(){
//        return jobservice.getLabourJobCountsWithId();
//  }*/
//    //...............................dashboard - pie chart -------------------------------
//    /*
//    @GetMapping("/dashboard/g_demandjob")
//    public ResponseEntity<List<Object[]>> getJobDemandData() {
//        List<Object[]> jobDemandData = jobservice.demandJobDetail();
//        return new ResponseEntity<>(jobDemandData, HttpStatus.OK);
//    }*/
//
//
//}
