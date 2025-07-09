package com.batchtraining.mk.springbatch.reader;

import com.batchtraining.mk.springbatch.model.StudentJson;
import com.batchtraining.mk.springbatch.model.StudentXml;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class XmlItemReaderFactory {

    @Bean
    @StepScope
    //jobParameter on run config parameters
    public StaxEventItemReader<StudentXml> xmlItemReader(
            @Value("#{jobParameters['inputFile']}") String filePath) {

        StaxEventItemReader<StudentXml> reader = new StaxEventItemReader<StudentXml>();
        reader.setResource(new FileSystemResource(filePath));
        reader.setFragmentRootElementName("student");
        reader.setUnmarshaller(new Jaxb2Marshaller() {
            {
                setClassesToBeBound(StudentXml.class);
            }
        });

        return reader;
    }
}
