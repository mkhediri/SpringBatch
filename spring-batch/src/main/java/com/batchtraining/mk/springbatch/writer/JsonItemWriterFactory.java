package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.model.StudentJdbc;
import com.batchtraining.mk.springbatch.model.StudentJson;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Date;
import java.util.List;

@Configuration
public class JsonItemWriterFactory {

    @Bean
    @StepScope
    public JsonFileItemWriter<StudentJson> jsonItemWriter(
            @Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource) {
        JsonFileItemWriter<StudentJson> writer = new JsonFileItemWriter<StudentJson>(fileSystemResource,
                new JacksonJsonObjectMarshaller<StudentJson>()) {
            @Override
            public String doWrite(Chunk<? extends StudentJson> items) {
                items.forEach(item -> {
                    if(item.getId() == 3) {
                        System.out.println("Inside json file item writer");
                        throw new NullPointerException();
                    }
                });
                return super.doWrite(items);
            }
        };
        return writer;
    }
}
