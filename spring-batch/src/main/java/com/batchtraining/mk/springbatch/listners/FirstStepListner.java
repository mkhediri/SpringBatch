package com.batchtraining.mk.springbatch.listners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListner implements StepExecutionListener {

    @Override
    public void beforeStep(org.springframework.batch.core.StepExecution stepExecution) {
        System.out.println("Before Step " + stepExecution.getStepName());
        System.out.println("Job Exec Context " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Step Exec Context " + stepExecution.getExecutionContext());
        stepExecution.getExecutionContext().put("sec", "sec value");
    }

    @Override
    public ExitStatus afterStep(org.springframework.batch.core.StepExecution stepExecution) {
        System.out.println("After Step " + stepExecution.getStepName());
        System.out.println("Job Exec Context " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Step Exec Context " + stepExecution.getExecutionContext());
        return null;
    }
}
