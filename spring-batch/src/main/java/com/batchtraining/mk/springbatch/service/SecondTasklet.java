package com.batchtraining.mk.springbatch.service;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class SecondTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("This is the second tasklet step");
        System.out.println("Chunk Context " + chunkContext.getStepContext().getJobExecutionContext());
        return RepeatStatus.FINISHED;
    }

}
