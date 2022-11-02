package com.huvenet.loatong.batch.test;

import com.huvenet.loatong.batch.dto.CoinMarket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JsonJob2 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 3;

    @Bean
    public Job jsonJob2_batchJob() {
        return jobBuilderFactory.get("jsonJob2")
                .start(jsonJob2_batchStep())
                .build();
    }

    @Bean
    public Step jsonJob2_batchStep() {
        return stepBuilderFactory.get("jsonJob2_batchStep")
                .<CoinMarket, CoinMarket>chunk(chunkSize)
                .reader(jsonJob2_Reader())
                .processor(jsonJob2_prosessor())
                .writer(jsonJob2_Writer())
                .build();
    }

    private ItemProcessor<CoinMarket, CoinMarket> jsonJob2_prosessor() {
        return coinMarket -> {
           if(coinMarket.getMarket().startsWith("KRW-")) {
                return new CoinMarket(coinMarket.getMarket(), coinMarket.getKorean_name(), coinMarket.getEnglish_name());
           } else {
               return null;
           }
        };
    }

    @Bean
    public JsonItemReader<CoinMarket> jsonJob2_Reader() {
        return new JsonItemReaderBuilder<CoinMarket>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(CoinMarket.class))
                .resource(new ClassPathResource("test/jsonJob1_Input.json"))
                .name("jsonJob2_Reader")
                .build();
    }

    @Bean
    public JsonFileItemWriter<CoinMarket> jsonJob2_Writer() {
        return new JsonFileItemWriterBuilder<CoinMarket>()
                .name("jsonJob2_Writer")
                .resource(new FileSystemResource("BATCH/src/main/resources/test/jsonJob2_Output.json"))
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .build();
    }
}
