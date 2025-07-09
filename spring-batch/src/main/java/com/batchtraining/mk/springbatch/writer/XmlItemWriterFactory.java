package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.model.StudentJdbc;
import com.batchtraining.mk.springbatch.model.StudentJson;
import com.batchtraining.mk.springbatch.model.StudentXml;
import com.sun.source.tree.SwitchTree;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class XmlItemWriterFactory {

    @Bean
    @StepScope
    public StaxEventItemWriter<StudentJdbc> xmlItemWriter(
            @Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource) {
        StaxEventItemWriter<StudentJdbc> writer = new StaxEventItemWriter<>();
        writer.setResource(fileSystemResource);
        writer.setRootTagName("students");
        writer.setMarshaller(new Jaxb2Marshaller() {
            {
                setClassesToBeBound(StudentJdbc.class);
            }
        });
        return writer;
    }
}
