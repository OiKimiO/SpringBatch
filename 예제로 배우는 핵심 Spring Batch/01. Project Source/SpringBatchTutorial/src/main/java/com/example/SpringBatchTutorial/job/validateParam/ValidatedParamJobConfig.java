package com.example.SpringBatchTutorial.job.validateParam;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.SpringBatchTutorial.job.validateParam.validator.FileParamValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * desc : 파일 이름 파라미터 전달 그리고 검증
 * run  : --spring.batch.job.names=validatedParamStep -fileName=test.csv
 * */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ValidatedParamJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job validatedParamJob(Step validatedParamStep) {
		return jobBuilderFactory.get("validatedParamStep")
							    .incrementer(new RunIdIncrementer())
							    // .validator(new FileParamValidator())
							    .validator(multipleValidator())
							    .start(validatedParamStep)
							    .build();
	}

	private CompositeJobParametersValidator multipleValidator() {
		CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
		validator.setValidators(Arrays.asList(new FileParamValidator()));
		
		return validator;
	}
	
	@JobScope
	@Bean
	public Step validatedParamStep(Tasklet validatedParamTasklet) {
		return stepBuilderFactory.get("validatedParamStep")
								 .tasklet(validatedParamTasklet)
								 .build();
	}

	@StepScope
	@Bean
	public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, 
										ChunkContext chunkContext) throws Exception {
				log.info("validated Param Tasklet");
				
				return RepeatStatus.FINISHED;
			}
		};
	}
}
