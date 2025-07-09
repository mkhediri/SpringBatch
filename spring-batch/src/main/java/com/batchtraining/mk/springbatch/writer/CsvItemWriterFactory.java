package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.model.StudentJdbc;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import java.util.Date;

@Configuration
public class CsvItemWriterFactory {

    @Bean
    @StepScope
    public FlatFileItemWriter<StudentJdbc> csvItemWriter(
            @Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource) {
        FlatFileItemWriter<StudentJdbc> writer = new FlatFileItemWriter<StudentJdbc>();
        writer.setResource(fileSystemResource);
        writer.setHeaderCallback(writer1 -> writer1.write("Id,First Name,Last Name,Email"));
        writer.setLineAggregator(new DelimitedLineAggregator<StudentJdbc>() {
            {
                setFieldExtractor(new BeanWrapperFieldExtractor<StudentJdbc>() {
                    {
                        setNames(new String[]{"id", "firstName", "lastName", "email"});
                    }
                });
            }
        });

        writer.setFooterCallback(writer1 -> {writer1.write("Created @ " + new Date());} );

        return writer;
    }
}