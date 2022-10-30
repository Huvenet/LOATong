package com.huvenet.loatong.batch.test;

import com.huvenet.loatong.batch.dto.OneDto;
import com.huvenet.loatong.batch.testConfig.TextJob2LineAggreagator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TextJob2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 3;

    @Bean
    public Job textJob2_build() {
        return jobBuilderFactory.get("textJob2")
                .start(textJob2_batchStep1())
                .build();
    }

    @Bean
    public Step textJob2_batchStep1() {
        return stepBuilderFactory.get("textJob2_batchStep1")
                .<OneDto, OneDto>chunk(chunkSize)
                .reader(textJob2_FileReader())
                .writer(textJob2_FileWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<OneDto> textJob2_FileReader() {
        FlatFileItemReader<OneDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("test/textJob2_Input.txt"));
        flatFileItemReader.setLineMapper(((line, lineNumber) -> new OneDto(lineNumber + "_" + line)));
        return flatFileItemReader;
    }

    @Bean
    public FlatFileItemWriter<OneDto> textJob2_FileWriter() {
        return new FlatFileItemWriterBuilder<OneDto>()
                .name("textJob2_FileWriter")
                .resource(new FileSystemResource("BATCH/src/main/resources/test/textJob2_Output.txt"))
                .lineAggregator(new TextJob2LineAggreagator<>())
                .build();
    }
}
