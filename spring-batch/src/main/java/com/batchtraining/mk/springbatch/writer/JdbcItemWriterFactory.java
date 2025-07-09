package com.batchtraining.mk.springbatch.writer;

import com.batchtraining.mk.springbatch.model.StudentCsv;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JdbcItemWriterFactory {

    @Bean
    public JdbcBatchItemWriter<StudentCsv> jdbcItemWriter(@Qualifier("universitydatasource") DataSource universitydatasource) {
        JdbcBatchItemWriter<StudentCsv> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(universitydatasource);
        /*writer.setSql("INSERT INTO student (id, first_name, last_name, email) VALUES (:id, :firstName, :lastName, :email)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<StudentCsv>());*/

        //using prepared statement
        writer.setSql("INSERT INTO student (id, first_name, last_name, email) VALUES (?, ?, ?, ?)");
        writer.setItemPreparedStatementSetter((item, ps) -> {
            ps.setLong(1, item.getId());
            ps.setString(2, item.getFirstName());
            ps.setString(3, item.getLastName());
            ps.setString(4, item.getEmail());
        });
        return writer;
    }
}
