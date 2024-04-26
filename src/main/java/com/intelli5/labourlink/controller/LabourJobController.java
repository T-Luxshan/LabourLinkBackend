/*package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.service.LabourJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LabourJobController {
    @Autowired
    private LabourJobService labourJobService;

    @GetMapping("/labour-job-data")
    public List<Object[]> getLabourJobCountsWithId() {
        return labourJobService.getLabourJobCountsWithId();
    }
}*/