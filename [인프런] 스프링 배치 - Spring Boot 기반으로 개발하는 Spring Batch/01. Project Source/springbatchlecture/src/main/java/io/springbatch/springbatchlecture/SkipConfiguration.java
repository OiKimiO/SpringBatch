package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SkipConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job_5")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String,String>chunk(5)
                .reader(new ItemReader<String>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        if(i == 3){
                            throw new NoSkippableException("skip");
                        }
                        System.out.println("ItemReader : " + i);
                        return i > 20 ? null: String.valueOf(i);
                    }
                })
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                // .skipPolicy(new NeverSkipItemSkipPolicy())
                .noSkip(NoSkippableException.class)
                .skipLimit(2)
                // .skip(SkippableException.class) // SkippableException.class가 발생하면 skip할 것
                // .skipLimit(3) // skip이 N번만 나면 오류가 나지 않게 함
                .build();
    }

    @Bean
    public SkipPolicy limitCheckingItemSkipPolicy() {

        Map<Class<? extends Throwable>,Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(SkippableException.class,true);
        exceptionClass.put(NoSkippableException.class,false);

        LimitCheckingItemSkipPolicy limitCheckingItemSkipPolicy = new LimitCheckingItemSkipPolicy(4,exceptionClass);

        return limitCheckingItemSkipPolicy;
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return new SkipItemWriter();
    }

    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return new SkipItemProcessor();
    }
}
