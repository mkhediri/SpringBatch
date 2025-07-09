package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.entitie.UserDb2;
import com.batchtraining.mk.springbatch.model.StudentCsv;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JpaItemWriterFactory {

    @Autowired
    @Qualifier("universityEntityManagerFactory")
    private EntityManagerFactory universityEntityManagerFactory;

    @Bean
    public JpaItemWriter<UserDb2> jpaItemWriter() {
        JpaItemWriter<UserDb2> writer = new JpaItemWriter<>();

        writer.setEntityManagerFactory(universityEntityManagerFactory);


        return writer;
    }
}
