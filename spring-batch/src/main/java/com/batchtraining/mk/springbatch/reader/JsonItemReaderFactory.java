package com.batchtraining.mk.springbatch.reader;

import com.batchtraining.mk.springbatch.model.StudentCsv;
import com.batchtraining.mk.springbatch.model.StudentJson;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class JsonItemReaderFactory {

    @Bean
    @StepScope
    public JsonItemReader<StudentJson> jsonItemReader(
            @Value("#{jobParameters['inputFile']}") String filePath) {

        JsonItemReader<StudentJson> reader = new JsonItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        reader.setJsonObjectReader(
                new JacksonJsonObjectReader<>(StudentJson.class)
        );

        /*//read only the 8 first item
        reader.setCurrentItemCount(8);*/

        //skip the first two items
        reader.setCurrentItemCount(2);

        return reader;
    }
}
