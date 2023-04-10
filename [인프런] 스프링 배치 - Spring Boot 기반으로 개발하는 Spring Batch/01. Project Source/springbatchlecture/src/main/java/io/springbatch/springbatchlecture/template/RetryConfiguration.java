package io.springbatch.springbatchlecture.template;

import io.springbatch.springbatchlecture.RetryableExceptixon;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class RetryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job_13")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .<String, Customer>chunk(5)
                                 .reader(reader())
                                 .processor(processor())
                                 .writer(items -> items.forEach(item-> System.out.println(item)))
                                 .faultTolerant()
                                 //.skip(RetryableExceptixon.class)
                                 //.skipLimit(2)
                                 //.retry(RetryableExceptixon.class)
                                 //.retryLimit(2)
                                 //.retryPolicy(retryPolicy())
                                 .build();
    }

    @Bean
    public ItemProcessor<? super String, Customer> processor() {
        return new RetryItemProcessor2();
    }

    @Bean
    public ItemReader<String> reader() {
        List<String> items = new ArrayList<>();
        for(int i = 0; i <= 30; i++){
            items.add(String.valueOf(i));
        }
        return new ListItemReader<>(items);
    }

    @Bean
    public RetryPolicy retryPolicy(){
        Map<Class<? extends Throwable>,Boolean> exceptionClass = new HashMap();
        exceptionClass.put(RetryableExceptixon.class,true);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2, exceptionClass);
        return simpleRetryPolicy;
    }

    @Bean
    public RetryTemplate retryTemplate(){
        Map<Class<? extends Throwable>,Boolean> exceptionClass = new HashMap();
        exceptionClass.put(RetryableExceptixon.class,true);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(2000); // 이 값이 있으면 Retry기능 자체가 2초 정도 지연된 상태에서 진행됨

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2,exceptionClass);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}