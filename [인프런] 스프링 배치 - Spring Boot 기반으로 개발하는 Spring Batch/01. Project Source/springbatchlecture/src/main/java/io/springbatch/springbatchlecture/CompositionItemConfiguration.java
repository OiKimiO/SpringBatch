package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class CompositionItemConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job4")
                                .start(step1())
                                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<String,String>chunk(10)
                .reader(new ItemReader<String>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception,
                                                UnexpectedInputException,
                                                ParseException,
                                                NonTransientResourceException {
                        i++;
                        return i > 10 ? null:"oho";
                    }
                })
                .processor(customItemProcessor())
                .writer(items-> System.out.println(items))
                .build();
    }

    @Bean
    public ItemProcessor<? super String, String> customItemProcessor() {

        List itemProcessor = new ArrayList();
        itemProcessor.add(new CustomItemProcessor_1());
        itemProcessor.add(new CustomItemProcessor2_1());

        return new CompositeItemProcessorBuilder<>()
                .delegates(itemProcessor)
                .build();
    }

}
