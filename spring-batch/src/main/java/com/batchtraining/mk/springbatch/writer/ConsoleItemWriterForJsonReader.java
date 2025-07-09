package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.model.StudentJson;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleItemWriterForJsonReader implements ItemWriter<StudentJson> {

        @Override
        public void write(Chunk<? extends StudentJson> chunk) throws Exception {
        System.out.println("Inside Item Writer");
        chunk.forEach(System.out::println);
    }
}
