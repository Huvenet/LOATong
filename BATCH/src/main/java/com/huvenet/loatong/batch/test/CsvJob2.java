package com.huvenet.loatong.batch.test;

import com.huvenet.loatong.batch.dto.TwoDto;
import com.huvenet.loatong.batch.testConfig.CsvJob2BeanWrapperFieldExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CsvJob2 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 3;

    @Bean
    public Job csvJob2_batchBuild() throws Exception {
        return jobBuilderFactory.get("csvJob2")
                .start(csvJob2_batchStep1())
                .build();
    }

    @Bean
    public Step csvJob2_batchStep1() throws Exception {
        return stepBuilderFactory.get("csvJob2_batchStep1")
                .<TwoDto, TwoDto>chunk(chunkSize)
                .reader(csvJob2_Reader())
                .writer(csvJob2_Writer(new FileSystemResource("BATCH/src/main/resources/test/csvJob2_Output.csv")))
                .build();
    }

    @Bean
    public FlatFileItemWriter<TwoDto> csvJob2_Writer(Resource resource) throws Exception {
        CsvJob2BeanWrapperFieldExtractor<TwoDto> csvJob2BeanWrapperFieldExtractor = new CsvJob2BeanWrapperFieldExtractor<>();
        csvJob2BeanWrapperFieldExtractor.setNames(new String[]{"one", "two"});
        csvJob2BeanWrapperFieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<TwoDto> dtoDelimitedLineAggregator = new DelimitedLineAggregator<>();
        dtoDelimitedLineAggregator.setDelimiter("@");
        dtoDelimitedLineAggregator.setFieldExtractor(csvJob2BeanWrapperFieldExtractor);

        return new FlatFileItemWriterBuilder<TwoDto>().name("csvJob2_FileWriter")
                .resource(resource)
                .lineAggregator(dtoDelimitedLineAggregator)
                .build();
    }

    @Bean
    public FlatFileItemReader<TwoDto> csvJob2_Reader() {
        FlatFileItemReader<TwoDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("test/csvJob1_Input.csv"));
        flatFileItemReader.setLinesToSkip(1);

        DefaultLineMapper<TwoDto> dtoDefaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("one", "two");
        delimitedLineTokenizer.setDelimiter(",");

        BeanWrapperFieldSetMapper<TwoDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(TwoDto.class);

        dtoDefaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        dtoDefaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(dtoDefaultLineMapper);
        return flatFileItemReader;
    }
}
