package com.huvenet.loatong.batch.test;

import com.huvenet.loatong.batch.dto.CoinMarket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JsonJob1 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 3;

    @Bean
    public Job jsonJob1_batchJob() {
        return jobBuilderFactory.get("jsonJob1")
                .start(jsonJob1_batchStep())
                .build();
    }

    @Bean
    public Step jsonJob1_batchStep() {
        return stepBuilderFactory.get("jsonJob1_batchStep")
                .<CoinMarket, CoinMarket>chunk(chunkSize)
                .reader(jsonJob1_Reader())
                .writer(coinMarket -> coinMarket.stream().forEach(coinMarket2 -> {
                    log.debug(coinMarket2.toString());
                }))
                .build();
    }

    @Bean
    public JsonItemReader<CoinMarket> jsonJob1_Reader() {
        return new JsonItemReaderBuilder<CoinMarket>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(CoinMarket.class))
                .resource(new ClassPathResource("test/jsonJob1_Input.json"))
                .name("jsonJob1_Reader")
                .build();
    }
}
