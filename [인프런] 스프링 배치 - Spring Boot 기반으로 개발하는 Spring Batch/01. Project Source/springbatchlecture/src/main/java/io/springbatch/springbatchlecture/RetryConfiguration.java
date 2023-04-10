package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RetryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job_10")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .<String, String>chunk(5)
                                 .reader(reader())
                                 .processor(processor())
                                 .writer(items -> items.forEach(item-> System.out.println(item)))
                                 .faultTolerant()
                                 .retry(RetryableExceptixon.class)
                                 .retryLimit(2)
                                 .build();
    }

    @Bean
    public ItemProcessor<? super String, String> processor() {
        return new RetryItemProcessor();
    }

    @Bean
    public ItemReader<String> reader() {
        List<String> items = new ArrayList<>();
        for(int i = 0; i <= 30; i++){
            items.add(String.valueOf(i));
        }
        return new ListItemReader<>(items);
    }
}