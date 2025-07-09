package com.batchtraining.mk.springbatch.listners;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListner implements JobExecutionListener {

    @Override
    public void beforeJob(org.springframework.batch.core.JobExecution jobExecution) {
        System.out.println("Before Job " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job Parameters " + jobExecution.getJobParameters());
        System.out.println("Job Exec Context " + jobExecution.getExecutionContext());

        jobExecution.getExecutionContext().put("jec", "jec value");
    }

    @Override
    public void afterJob(org.springframework.batch.core.JobExecution jobExecution) {
        System.out.println("After job "+ jobExecution.getJobInstance().getJobName());
        System.out.println("Job Parameters " + jobExecution.getJobParameters());
        System.out.println("Job Exec Context " + jobExecution.getExecutionContext());
    }

}
