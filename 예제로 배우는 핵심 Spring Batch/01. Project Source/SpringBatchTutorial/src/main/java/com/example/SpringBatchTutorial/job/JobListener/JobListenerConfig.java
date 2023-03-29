package com.example.SpringBatchTutorial.job.JobListener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * desc : Job Listener Tasklet 출력
 * run  : --spring.batch.job.names=jobListenerJob
 * */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobListenerConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job jobListenerJob(Step jobListenerStep) {
		return jobBuilderFactory.get("jobListenerJob") 					// jobListenerJob을 등록
							    .incrementer(new RunIdIncrementer())    // 
							    .listener(new JobLoggerListener())		// 리스너를 등록하여 Job을 가로챔
							    .start(jobListenerStep)					// jobListenerStep에서 정의한 내용을 주입받음
							    .build();
	}
	
	/**
	 * jobListenerStep의 빈은 아래의 Tasklet 생성자를 주입받음
	 *   - 
	 * */
	@JobScope
	@Bean
	public Step jobListenerStep(Tasklet jobListenerTasklet) {
		return stepBuilderFactory.get("jobListenerStep")      // jobListenerStep을 등록
								 .tasklet(jobListenerTasklet) // jobListenerTasklet에서 정의한 내용을 주입 받음
								 .build();
	}
	
	/**
	 * Tasklet 인터페이스의 구현체를 직접 구현해서 사용
	 *   - 구현 방법 : Tasklet(){...}
	 * */
	@StepScope
	@Bean
	public Tasklet jobListenerTasklet() {
		return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, 
										ChunkContext chunkContext) throws Exception {
				log.info("Job Listener Tasklet");
				return RepeatStatus.FINISHED;
			}
		};
	}
	
}
