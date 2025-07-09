package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.model.StudentXml;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleItemWriterForXmlReader implements ItemWriter<StudentXml> {

    @Override
    public void write(Chunk<? extends StudentXml> chunk) throws Exception {
        System.out.println("Inside Item Writer");
        chunk.forEach(System.out::println);
    }
    }