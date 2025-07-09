package com.batchtraining.mk.springbatch.reader;

import com.batchtraining.mk.springbatch.entitie.UserDb1;
import com.batchtraining.mk.springbatch.model.StudentJdbc;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class JpaItemReaderFactory {

    @Autowired
    @Qualifier("universityEntityManagerFactory")
    private EntityManagerFactory universityEntityManagerFactory;

    @Bean
    @StepScope
    public JpaCursorItemReader<UserDb1> jpaItemReader(JpaCursorItemReader jpaCursorItemReader) {
        JpaCursorItemReader<UserDb1> reader = new JpaCursorItemReader<>();

        reader.setEntityManagerFactory(universityEntityManagerFactory);
        reader.setQueryString("from UserDb1");

        return reader;
    }
}
