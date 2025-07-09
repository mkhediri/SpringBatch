package com.batchtraining.mk.springbatch.reader;

import com.batchtraining.mk.springbatch.model.StudentCsv;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class CsvItemReaderFactory {

    @Bean
    @StepScope
    public FlatFileItemReader<StudentCsv> csvItemReader(
            @Value("#{jobParameters['inputFile']}") String filePath) {

        FlatFileItemReader<StudentCsv> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        reader.setLinesToSkip(1);

        DefaultLineMapper<StudentCsv> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("ID", "First Name", "Last Name", "Email");

        BeanWrapperFieldSetMapper<StudentCsv> mapper = new BeanWrapperFieldSetMapper<StudentCsv>();
        mapper.setTargetType(StudentCsv.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);

        reader.setLineMapper(lineMapper);

        //another way to implement code
        /*reader.setLineMapper(new DefaultLineMapper<StudentCsv>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {{
                    setNames("ID", "First Name", "Last Name", "Email");
                }});
                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>() {{
                    setTargetType(StudentCsv.class);
                }});
            }
        });*/

        return reader;
    }
}
