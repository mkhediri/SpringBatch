package com.batchtraining.mk.springbatch.processor;

import com.batchtraining.mk.springbatch.entitie.UserDb1;
import com.batchtraining.mk.springbatch.entitie.UserDb2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DbToDbProcessor implements ItemProcessor<UserDb1, UserDb2> {

    @Override
    public UserDb2 process(UserDb1 item) throws Exception {
        UserDb2 userDb2 = new UserDb2();

        userDb2.setId(item.getId()+1);
        userDb2.setFirstName(item.getFirstName());
        userDb2.setLastName(item.getLastName());
        userDb2.setEmail(item.getEmail());
        return userDb2;
    }
}