package com.huvenet.loatong.batch.test;

import com.huvenet.loatong.batch.dto.TwoDto;
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
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FixedLengthJob2 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 3;

    @Bean
    public Job fixedLengthJob2_batchBuild() throws Exception {
        return jobBuilderFactory.get("fixedLengthJob2")
                .start(fixedLengthJob2_batchStep1())
                .build();
    }

    @Bean
    public Step fixedLengthJob2_batchStep1() throws Exception {
        return stepBuilderFactory.get("fixedLengthJob2_batchStep1")
                .<TwoDto, TwoDto>chunk(chunkSize)
                .reader(fixedLengthJob2_Reader())
                .writer(fixedLengthJob2_Writer(new FileSystemResource("BATCH/src/main/resources/test/fixedLengthJob2_Output.txt"))).build();
    }

    @Bean
    public FlatFileItemWriter<TwoDto> fixedLengthJob2_Writer(Resource outputResource) throws Exception {
        BeanWrapperFieldExtractor<TwoDto> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        beanWrapperFieldExtractor.setNames(new String[] {"one", "two"});
        beanWrapperFieldExtractor.afterPropertiesSet();

        FormatterLineAggregator<TwoDto> formatterLineAggregator = new FormatterLineAggregator<>();
        formatterLineAggregator.setFormat("%-5s###%5s");
        formatterLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

        return new FlatFileItemWriterBuilder<TwoDto>().lineAggregator(formatterLineAggregator)
                .resource(outputResource)
                .name("fixedLengthJob2_FileWriter")
                .build();
    }

    @Bean
    public FlatFileItemReader<TwoDto> fixedLengthJob2_Reader() {
        FlatFileItemReader<TwoDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("test/fixedLengthJob2_Input.txt"));
        flatFileItemReader.setLinesToSkip(1);

        DefaultLineMapper<TwoDto> dtoDefaultLineMapper = new DefaultLineMapper<>();

        FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();
        fixedLengthTokenizer.setNames("one", "two");
        fixedLengthTokenizer.setColumns(new Range(1, 5), new Range(6, 10));

        BeanWrapperFieldSetMapper<TwoDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(TwoDto.class);

        dtoDefaultLineMapper.setLineTokenizer(fixedLengthTokenizer);
        dtoDefaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(dtoDefaultLineMapper);
        return flatFileItemReader;
    }
}
