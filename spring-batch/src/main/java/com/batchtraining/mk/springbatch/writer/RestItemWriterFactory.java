package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.model.StudentResponse;
import com.batchtraining.mk.springbatch.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RestItemWriterFactory {

    private final StudentService studentService;

    @Bean
    @StepScope
    public ItemWriterAdapter<StudentResponse> restItemWriter() {
        ItemWriterAdapter<StudentResponse> writer = new ItemWriterAdapter<>();
        writer.setTargetObject(studentService);
        writer.setTargetMethod("restCallToCreateStudent");
        return writer;
    }
}
