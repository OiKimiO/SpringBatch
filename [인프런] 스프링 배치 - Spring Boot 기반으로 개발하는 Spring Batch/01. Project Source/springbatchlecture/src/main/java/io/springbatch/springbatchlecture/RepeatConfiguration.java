package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
public class RepeatConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job_1")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
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
                        return i > 3? null : "item"+1;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    int i = 0;
                    RepeatTemplate repeatTemplate = new RepeatTemplate();

                    @Override
                    public String process(String item) throws Exception {
                        /* Completion 예제
                            // SimpleCompletionPolicy(3)은 repeatTemplate.iterate가 3번 반복된다는 의미
                            repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(3));
                            // TimeoutTerminationPolicy(3000)은 repeatTemplate.iterate가 3초 동안 반복된다는 의미
                            repeatTemplate.setCompletionPolicy(new TimeoutTerminationPolicy(3000));


                            // Complete관련 정책을 동시에 실행
                            // 정책이 동시에 진행되다가 두 정책 중 하나가 만족되면 종료함
                            CompositeCompletionPolicy completionPolicy = new CompositeCompletionPolicy();
                            CompletionPolicy[] completionPolicies = new CompletionPolicy[]{
                                                                                        new SimpleCompletionPolicy(3),
                                                                                        new TimeoutTerminationPolicy(3000)};
                            completionPolicy.setPolicies(completionPolicies);
                            repeatTemplate.setCompletionPolicy(completionPolicy);
                        */
                        // SimpleLimitExceptionHandler(3) 예외가 발생해도 3번까지는 실행함
                        repeatTemplate.setExceptionHandler(simpleLimitExceptionHandler());

                        repeatTemplate.iterate(new RepeatCallback() {

                            @Override
                            public RepeatStatus doInIteration(RepeatContext repeatContext) throws Exception {
                                System.out.println("repeatTemplate is testing");
                                throw new RuntimeException("Exception is ocurred");
                                // return RepeatStatus.CONTINUABLE;
                            }
                        });

                        return item;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> list) throws Exception {

                    }
                })
                .build();
    }

    @Bean
    public ExceptionHandler simpleLimitExceptionHandler(){
        return new SimpleLimitExceptionHandler(3);
    }
}
