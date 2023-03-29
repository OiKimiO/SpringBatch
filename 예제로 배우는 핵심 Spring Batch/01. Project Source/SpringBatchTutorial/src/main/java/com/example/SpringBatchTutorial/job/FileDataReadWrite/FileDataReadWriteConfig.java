package com.example.SpringBatchTutorial.job.FileDataReadWrite;

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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;

import com.example.SpringBatchTutorial.domain.accounts.Accounts;
import com.example.SpringBatchTutorial.domain.accounts.AccountsRepository;
import com.example.SpringBatchTutorial.domain.orders.Orders;
import com.example.SpringBatchTutorial.domain.orders.OrdersRepository;
import com.example.SpringBatchTutorial.domain.player.Player;
import com.example.SpringBatchTutorial.domain.player.PlayerYears;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * desc : 주문 테이블 -> 정산 테이블 데이터 이관
 * run  : --spring.batch.job.names=fileReadWriteJob
 * */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileDataReadWriteConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job fileReadWriteJob(Step fileReadWriteStep) {
		return jobBuilderFactory.get("fileReadWriteJob") 				// fileReadWriteJob을 등록
							    .incrementer(new RunIdIncrementer())    // 
							    .start(fileReadWriteStep)				// jobListenerStep에서 정의한 내용을 주입받음
							    .build();
	}
	
	/**
	 * jobListenerStep의 빈은 아래의 Tasklet 생성자를 주입받음
	 *   - 
	 * */
	@JobScope
	@Bean
	public Step fileReadWriteStep(ItemReader playerItemReader,
								  ItemProcessor playerItemProcessor,
								  ItemWriter playerItemWriter) {
		return stepBuilderFactory.get("fileReadWriteStep")      // fileReadWriteStep을 등록
								 .<Player,PlayerYears>chunk(5)
								 .reader(playerItemReader)
				/*
				 * .writer(new ItemWriter() {
				 * 
				 * @Override public void write(List items) throws Exception {
				 * items.forEach(System.out::println); }
				 * 
				 * })
				 */
								 .processor(playerItemProcessor)
								 .writer(playerItemWriter)
								 .build();
	}
	
	/**
	 * 메서드 명을 기반으로 bean을 만드는 것 같음
	 * */
	@StepScope
	@Bean
	public ItemProcessor<Player, PlayerYears> playerItemProcessor(){
		return new ItemProcessor<Player, PlayerYears> (){

			@Override
			public PlayerYears process(Player player) throws Exception {
				return new PlayerYears(player);
			}

			
		};
	}
	
	@StepScope
	@Bean
	public FlatFileItemWriter<PlayerYears> playerItemWriter(){
		BeanWrapperFieldExtractor<PlayerYears> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] {"ID", "lastName", "position", "yearsExperience"});
		fieldExtractor.afterPropertiesSet();
		
		DelimitedLineAggregator<PlayerYears> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");
		lineAggregator.setFieldExtractor(fieldExtractor);
		
		FileSystemResource outputResource = new FileSystemResource("player_output.txt");
		
		return new FlatFileItemWriterBuilder<PlayerYears>()
					.name("playerItemWriter")
					.resource(outputResource)
					.lineAggregator(lineAggregator)
					.build();
	} 
	
	
	
	@JobScope
	@Bean
	public FlatFileItemReader<Player> playerItemReader(){
		return new FlatFileItemReaderBuilder()
				   .name("playerItemReader")
				   .resource(new FileSystemResource("Player.csv"))
				   .lineTokenizer(new DelimitedLineTokenizer())
				   .fieldSetMapper(new PlayerFieldSetMapper())
				   .linesToSkip(1)
				   .build();
	}
	
}
