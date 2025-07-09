package com.batchtraining.mk.springbatch.processor;

import com.batchtraining.mk.springbatch.model.StudentCsv;
import com.batchtraining.mk.springbatch.model.StudentJdbc;
import com.batchtraining.mk.springbatch.model.StudentJson;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstItemProcessor implements ItemProcessor<StudentCsv, StudentJson> {

    @Override
    public StudentJson process(StudentCsv item) throws Exception {
        System.out.println("Inside Item Processor");

        if(item.getId() == 6) {
            throw new NullPointerException();
        }

        StudentJson studentJson = new StudentJson();
        studentJson.setId(item.getId());
        studentJson.setFirstName(item.getFirstName());
        studentJson.setLastName(item.getLastName());
        studentJson.setEmail(item.getEmail());
        return studentJson;
    }
}
