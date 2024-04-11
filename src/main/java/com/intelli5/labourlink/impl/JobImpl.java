package com.intelli5.labourlink.impl;

import com.intelli5.labourlink.entity.Job;
import com.intelli5.labourlink.repository.JobRepo;
import com.intelli5.labourlink.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobImpl implements JobService {
    @Autowired
    private JobRepo jobRepository;
    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }
}
