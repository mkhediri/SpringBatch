package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.model.StudentResponse;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleItemWriterForRestReader implements ItemWriter<StudentResponse>  {

    @Override
    public void write(Chunk<? extends StudentResponse> chunk) throws Exception {
        System.out.println("Inside Item Writer");
        chunk.forEach(System.out::println);
    }
}
