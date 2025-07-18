package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.model.StudentJdbc;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleItemWriterForJdbcReader implements ItemWriter<StudentJdbc> {

    @Override
    public void write(Chunk<? extends StudentJdbc> chunk) throws Exception {
        System.out.println("Inside Item Writer");
        chunk.forEach(System.out::println);
    }
}
