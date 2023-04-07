package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class StepContributionConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                                .start(step1())
                                .next(step2())
                                .build();
    }

    @Bean
    public Step step2() {
        /*                             StepExecution               < [(5)apply()]StepContribution
           TaskletStep [(1)create()] > StepExecution [(2)create()] > StepContribution
           ChunkOrientedTasklet[(3)execute(contribution,chunkContext)]
              ItemReader(readCount, readSkipCount)              [(4)청크 프로세스의 변경 사항을 버퍼링]
              ItemProcessor(filterCount, processSkipCount)      [(4)청크 프로세스의 변경 사항을 버퍼링]
              ItemWriter(writeCount,writeSkipCOunt)             [(4)청크 프로세스의 변경 사항을 버퍼링]
         */
        return stepBuilderFactory.get("step2")
                                 .tasklet(new Tasklet() {
                                    @Override
                                    public RepeatStatus execute(StepContribution contribution,
                                                                ChunkContext chunkContext) throws Exception {
                                        System.out.println("step2를 실행");
                                        return RepeatStatus.FINISHED;
                                    }
                                 })
                                 .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .tasklet(new Tasklet() {
                                    @Override
                                    public RepeatStatus execute(StepContribution contribution,
                                                                ChunkContext chunkContext) throws Exception {
                                        System.out.println("step1를 실행");
                                        return RepeatStatus.FINISHED;
                                    }
                                 })
                                 .build();
    }
}
