package com.batchtraining.mk.springbatch.controller;

import com.batchtraining.mk.springbatch.request.JobParamsRequest;
import com.batchtraining.mk.springbatch.service.JobService;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    JobService jobService;

    @Autowired
    JobOperator jobOperator;

    @PostMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName, @RequestBody List<JobParamsRequest> jobParamsRequests) {
        jobService.startJob(jobName, jobParamsRequests);
        return "Job Started...";
    }

    @GetMapping("/stop/{executionId}")
    public String stopJob(@PathVariable Long executionId) {
        try {
            jobOperator.stop(executionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Job Stopped...";
    }
}
