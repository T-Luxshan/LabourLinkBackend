package com.intelli5.labourlink.service.impl;

import com.intelli5.labourlink.entity.Job;
import com.intelli5.labourlink.repository.JobRepository;
import com.intelli5.labourlink.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;
    @Override
    public void createJob(Job job) {

        jobRepository.save(job);
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public boolean existsByJobName(String jobName) {
        return jobRepository.existsByJobName(jobName);
    }

    //...............................dashboard - pie chart -------------------------------
   /* public List<Object[]>  demandJobDetail(){
        LocalDate startDate = LocalDate.now().minusDays(1);
        return jobRepository. demandJobDetail(startDate);
    }
*/
   // public List<Object[]>getLabourJobCountsWithId(){}
}
