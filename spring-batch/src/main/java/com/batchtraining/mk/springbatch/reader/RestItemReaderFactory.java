package com.batchtraining.mk.springbatch.reader;

import com.batchtraining.mk.springbatch.model.StudentResponse;
import com.batchtraining.mk.springbatch.service.StudentService;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestItemReaderFactory {

    @Autowired
    private StudentService studentService;

    @Bean
    public ItemReaderAdapter<StudentResponse> restItemReaderAdpater() {
        ItemReaderAdapter<StudentResponse> reader = new ItemReaderAdapter<>();
        reader.setTargetObject(studentService);
        reader.setTargetMethod("getStudent");
        return reader;
    }
}
