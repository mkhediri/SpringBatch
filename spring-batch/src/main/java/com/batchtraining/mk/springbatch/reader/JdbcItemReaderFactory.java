package com.batchtraining.mk.springbatch.reader;

import com.batchtraining.mk.springbatch.model.StudentJdbc;
import com.batchtraining.mk.springbatch.model.StudentJson;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class JdbcItemReaderFactory {

    //used for one datasource
    /*@Autowired
    private DataSource dataSource;*/

    //for metadata informations
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


    //for jdbc reader
    @Bean
    @ConfigurationProperties(prefix = "spring.universitydatasource")
    public DataSource universitydatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @StepScope
    public JdbcCursorItemReader<StudentJdbc> jdbcItemReader(JdbcCursorItemReader jdbcCursorItemReader) {

        JdbcCursorItemReader<StudentJdbc> reader = new JdbcCursorItemReader<StudentJdbc>();
        reader.setDataSource(universitydatasource());
        //its good practive to provide alias even its not nessessary, spring batch map the attributes to columns names
        reader.setSql("select id, first_name as firstName, last_name as lastName, email from student");
        reader.setRowMapper(new BeanPropertyRowMapper<StudentJdbc>() {
            {
                setMappedClass(StudentJdbc.class);
            }
        });
        //skip the two first lines
        reader.setCurrentItemCount(2);
        // Limit the number of items read to 8, used together with "setCurrentItemCount" to control the read range
        reader.setMaxItemCount(8);
        return reader;
    }
}
