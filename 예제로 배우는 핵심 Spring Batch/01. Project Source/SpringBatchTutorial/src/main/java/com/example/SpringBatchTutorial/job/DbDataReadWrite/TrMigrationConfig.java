package com.example.SpringBatchTutorial.job.DbDataReadWrite;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import com.example.SpringBatchTutorial.domain.accounts.Accounts;
import com.example.SpringBatchTutorial.domain.accounts.AccountsRepository;
import com.example.SpringBatchTutorial.domain.orders.Orders;
import com.example.SpringBatchTutorial.domain.orders.OrdersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * desc : 주문 테이블 -> 정산 테이블 데이터 이관
 * run  : --spring.batch.job.names=trMigrationJob
 * */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TrMigrationConfig {

	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job trMigrationJob(Step trMigrationStep) {
		return jobBuilderFactory.get("trMigrationJob") 					// trMigrationJob을 등록
							    .incrementer(new RunIdIncrementer())    // 
							    .start(trMigrationStep)					// jobListenerStep에서 정의한 내용을 주입받음
							    .build();
	}
	
	/**
	 * jobListenerStep의 빈은 아래의 Tasklet 생성자를 주입받음
	 *   - 
	 * */
	@JobScope
	@Bean
	public Step trMigrationStep(ItemReader trOrdersReader,
								ItemProcessor trOrdersProcessor,
								ItemWriter trOrdersWriter) {
		return stepBuilderFactory.get("trMigrationStep")      // trMigrationStep을 등록
								 .<Orders, Accounts>chunk(5)
								 .reader(trOrdersReader)
				/*
				 * .writer(new ItemWriter() {
				 * 
				 * @Override public void write(List items) throws Exception {
				 * items.forEach(System.out::println); } })
				 */
								 .processor(trOrdersProcessor) 
								 .writer(trOrdersWriter)
								 .build();
	}
	
	/*
	public RepositoryItemWriter<Accounts> trOrdersWriter(){
		return new RepositoryItemWriterBuilder<Accounts>()
				.repository(accountsRepository)
				.methodName("save")
				.build();
	}*/
	
	@StepScope
	@Bean
	public ItemWriter<Accounts> trOrdersWriter(){
		return new ItemWriter<Accounts>() {

			@Override
			public void write(List<? extends Accounts> items) throws Exception {
				items.forEach(item -> accountsRepository.save(item));
			}
			
		};
	}
	
	@StepScope
	@Bean
	public ItemProcessor<Orders, Accounts> trOrdersProcessor(){
		return new ItemProcessor<Orders, Accounts> (){

			@Override
			public Accounts process(Orders item) throws Exception {
				return new Accounts(item);
			}
			
		};
	}
	
	@StepScope
	@Bean
	public RepositoryItemReader<Orders> trOrdersReader(){
		return new RepositoryItemReaderBuilder<Orders>()
				.name("trOrdersReader")
				.repository(ordersRepository)
				.methodName("findAll")
				.pageSize(5)
				.arguments(Arrays.asList())
				.sorts(Collections.singletonMap("id", Sort.Direction.ASC))
				.build();
	}
}
