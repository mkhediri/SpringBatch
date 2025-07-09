package com.batchtraining.mk.springbatch.config;

import com.batchtraining.mk.springbatch.entitie.UserDb1;
import com.batchtraining.mk.springbatch.entitie.UserDb2;
import com.batchtraining.mk.springbatch.listener.MySkipListener;
import com.batchtraining.mk.springbatch.listners.FirstJobListner;
import com.batchtraining.mk.springbatch.listners.FirstStepListner;
import com.batchtraining.mk.springbatch.model.*;
import com.batchtraining.mk.springbatch.processor.DbToDbProcessor;
import com.batchtraining.mk.springbatch.processor.FirstItemProcessor;
import com.batchtraining.mk.springbatch.reader.FirstItemReader;
import com.batchtraining.mk.springbatch.service.SecondTasklet;
import com.batchtraining.mk.springbatch.writer.*;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class SampleJob {

    @Autowired
    private SecondTasklet secondTask;

    @Autowired
    FirstJobListner firstJobListner;

    @Autowired
    private FirstStepListner firstStepListner;

    @Autowired
    FirstItemReader firstItemReader;

    @Autowired
    FirstItemProcessor firstItemProcessor;

    @Autowired
    DbToDbProcessor dbToDbProcessor;

    @Autowired
    FirstItemWriter firstItemWriter;

    @Autowired
    private ConsoleItemWriterForCsvReader consoleItemWriterForCsvReader;

    @Autowired
    private ConsoleItemWriterForJsonReader consoleItemWriterForJsonReader;

    @Autowired
    private ConsoleItemWriterForXmlReader consoleItemWriterForXmlReader;

    @Autowired
    private ConsoleItemWriterForJdbcReader consoleItemWriterForJdbcReader;

    @Autowired
    private ConsoleItemWriterForRestReader consoleItemWriterForRestReader;

    @Autowired
    private MySkipListener skipListener;

    @Autowired
    @Qualifier("universityEntityManagerFactory")
    private EntityManagerFactory universityEntityManagerFactory;

    @Autowired
    @Qualifier("universityTransactionManager")
    JpaTransactionManager universityTransactionManager;

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

    //@Bean // commenter le @Bean permet de désactiver le job
    public Job firstJob(JobRepository jobRepository, Step firstStep, Step secondStep) {
        return new JobBuilder("First Job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(firstStep)
                .next(secondStep)
                .listener(firstJobListner)
                .build();
    }

    @Bean
    public Step firstStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager,
                          Tasklet firstTask) {
        return new StepBuilder("First Step", jobRepository)
                .tasklet(firstTask, transactionManager)
                .listener(firstStepListner)
                .build();
    }

    @Bean
    public Step secondStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager) {
        return new StepBuilder("Second Step", jobRepository)
                .tasklet(secondTask, transactionManager)
                .build();
    }

    @Bean
    public Tasklet firstTask() {
        return (contribution, chunkContext) -> {
            System.out.println("This is the first tasklet step");
            return RepeatStatus.FINISHED;
        };
    }

    /*@Bean
    public Tasklet secondTask() {
        return (contribution, chunkContext) -> {
            System.out.println("This is the second tasklet step");
            return RepeatStatus.FINISHED;
        };
    }*/

    @Bean
    public Job secondJob(JobRepository jobRepository, Step jpaChunkStep/*Step csvChunkStep*/) {
        return new JobBuilder("Second Job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(jpaChunkStep)
                .build();
    }

    /*@Bean
    public Step firstChunkStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager) {
            return new StepBuilder("First CHunk Step", jobRepository)
                    .<Integer, Long>chunk(4, transactionManager)
                    .reader(firstItemReader)
                    .writer(firstItemWriter)
                    .build();
    }*/

    //step for csv reader
    /*@Bean
    public Step csvChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<StudentCsv> csvItemReader) {
        return new StepBuilder("First CHunk Step", jobRepository)
                .<StudentCsv, StudentCsv>chunk(3, transactionManager)
                .reader(csvItemReader)
                .writer(csvItemWriter)
                .build();
    }*/

    //step for json reader
    /*@Bean
    public Step jsonChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JsonItemReader<StudentJson> jsonItemReader) {
        return new StepBuilder("Json CHunk Step", jobRepository)
                .<StudentJson, StudentJson>chunk(3, transactionManager)
                .reader(jsonItemReader)
                .writer(jsonItemWriter)
                .build();
    }*/

    /*//step for xml reader
    @Bean
    public Step xmlChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            StaxEventItemReader<StudentXml> xmlItemReader) {
        return new StepBuilder("Xml CHunk Step", jobRepository)
                .<StudentXml, StudentXml>chunk(3, transactionManager)
                .reader(xmlItemReader)
                .writer(xmlItemWriter)
                .build();
    }*/

    /*//step for jdbc reader
    @Bean
    public Step jdbcChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<StudentJdbc> jdbcItemReader) {
        return new StepBuilder("Xml CHunk Step", jobRepository)
                .<StudentJdbc, StudentJdbc>chunk(3, transactionManager)
                .reader(jdbcItemReader)
                .writer(jdbcItemWriter)
                .build();
    }*/

    /*//step for rest reader
    @Bean
    public Step restChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReaderAdapter<StudentResponse> restItemReaderAdpater) {
        return new StepBuilder("Rest CHunk Step", jobRepository)
                .<StudentResponse, StudentResponse>chunk(3, transactionManager)
                .reader(restItemReaderAdpater)
                .writer(consoleItemWriterForRestReader)
                .build();
    }*/


    //step for flat file writer
    /*@Bean
    public Step jdbcChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<StudentJdbc> jdbcItemReader,
            FlatFileItemWriter<StudentJdbc> csvItemWriter) {
        return new StepBuilder("Xml CHunk Step", jobRepository)
                .<StudentJdbc, StudentJdbc>chunk(3, transactionManager)
                .reader(jdbcItemReader)
                .writer(csvItemWriter)
                .build();
    }*/

    //step for json writer
    /*@Bean
    public Step jdbcChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<StudentJdbc> jdbcItemReader,
            JsonFileItemWriter<StudentJson> jsonItemWriter) {
        return new StepBuilder("Xml CHunk Step", jobRepository)
                .<StudentJdbc, StudentJson>chunk(3, transactionManager)
                .reader(jdbcItemReader)
                .processor(firstItemProcessor)
                .writer(jsonItemWriter)
                .build();
    }*/

/*    //step for xml writer
    @Bean
    public Step jdbcChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<StudentJdbc> jdbcItemReader,
            StaxEventItemWriter<StudentJdbc> xmlItemWriter) {
        return new StepBuilder("Xml CHunk Step", jobRepository)
                .<StudentJdbc, StudentJdbc>chunk(3, transactionManager)
                .reader(jdbcItemReader)
                .writer(xmlItemWriter)
                .build();
    }*/

    //step for jdbc writer
/*    @Bean
    public Step csvChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<StudentCsv> csvItemReader,
            JdbcBatchItemWriter<StudentCsv> jdbcItemWriter) {
        return new StepBuilder("Xml CHunk Step", jobRepository)
                .<StudentCsv, StudentCsv>chunk(3, transactionManager)
                .reader(csvItemReader)
                .writer(jdbcItemWriter)
                .build();
    }*/

    //step for rest writer
/*    @Bean
    public Step csvChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<StudentCsv> csvItemReader,
            ItemWriterAdapter<StudentResponse> restItemWriter) {
        return new StepBuilder("Xml CHunk Step", jobRepository)
                .<StudentCsv, StudentResponse>chunk(3, transactionManager)
                .reader(csvItemReader)
                .writer(restItemWriter)
                .build();
    }*/

    /*example for fault tolerance (use student_exeption for reader) to deal with format exeption
    using skip for exeption*//*
    @Bean
    public Step csvChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<StudentCsv> csvItemReader,
            JsonFileItemWriter<StudentJson> jsonItemWriter) {
        return new StepBuilder("Xml CHunk Step", jobRepository)
                .<StudentCsv, StudentJson>chunk(3, transactionManager)
                .reader(csvItemReader)
                .processor(firstItemProcessor)
                .writer(jsonItemWriter)
                .faultTolerant()
                .skip(Throwable.class)
                //skip one bad record, if you have two or more, throw an exception (skip limit 1 exceeded)
                .skipLimit(100)
                //skip all bad record
                //.skipPolicy(new AlwaysSkipItemSkipPolicy())
                //retry mecanism
                .retryLimit(1)
                .retry(Throwable.class)
                //listener for capturing bad record
                .listener(skipListener)
                .build();
    }*/


    //DB to DB migration using JPA reader and writer
    @Bean
    public Step jpaChunkStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JpaCursorItemReader<UserDb1> jpaItemReader,
            JpaItemWriter<UserDb2> jpaItemWriter) {
        return new StepBuilder("Xml CHunk Step", jobRepository)
                .<UserDb1, UserDb2>chunk(3, transactionManager)
                .reader(jpaItemReader)
                .processor(dbToDbProcessor)
                .writer(jpaItemWriter)
                .faultTolerant()
                .skip(Throwable.class)
                .skipLimit(100)
                .retryLimit(1)
                .retry(Throwable.class)
                .listener(skipListener)
                .transactionManager(universityTransactionManager)
                .build();
    }

}
