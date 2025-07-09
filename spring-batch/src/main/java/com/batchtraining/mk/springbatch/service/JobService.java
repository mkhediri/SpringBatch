package com.batchtraining.mk.springbatch.service;

import com.batchtraining.mk.springbatch.request.JobParamsRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    JobLauncher jobLauncher;

    /*@Qualifier("firstJob")
    @Autowired
    Job firstJob;*/

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

    //make job asynchrounous
    @Async
    public void startJob(String jobName, List<JobParamsRequest> jobParamsRequests) {
        /*Map<String, JobParameter<?>> params = new HashMap<>();
        params.put("currentTime", new JobParameter<>(System.currentTimeMillis(), Long.class));

        jobParamsRequests.forEach(jobParamsRequest -> {
            params.put(jobParamsRequest.getParamKey(), new JobParameter<>(jobParamsRequest.getParamValue(), String.class));
        });
        JobParameters jobsParameters = new JobParameters(params);

            try {
                JobExecution jobExecution = null;
                if (jobName.equals("First Job")) {
                    jobExecution = jobLauncher.run(firstJob, jobsParameters);
                }
                else if (jobName.equals("Second Job")) {
                    jobExecution = jobLauncher.run(secondJob, jobsParameters);
                }
                System.out.println("Job Execution ID " + jobExecution.getId());
            } catch (Exception e) {
                 System.out.println("Exception while starting service job");
            }*/
        }
    }
