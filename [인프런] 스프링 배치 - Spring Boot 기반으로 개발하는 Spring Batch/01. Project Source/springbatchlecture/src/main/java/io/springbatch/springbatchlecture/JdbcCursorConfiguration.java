package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class JdbcCursorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private int chunkSize = 10;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("Job2")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step4")
                .<Customer, Customer> chunk(chunkSize)
                .reader(customItemReader())
                .writer(customItemWriter())
                .build();
    }

    private ItemReader<Customer> customItemReader() {
        return new JdbcCursorItemReaderBuilder<Customer>().name("jdbcCursorItemReader")
                  .fetchSize(chunkSize)
                  .sql("select id, firstName, lastName from customer where firstName like ? order by lastName, firstName")
                  .beanRowMapper(Customer.class)
                  .queryArguments("A%")
                  .dataSource(dataSource)
                  .build();
    }

    @Bean
    public ItemWriter<Customer> customItemWriter() {
        return items -> {
                            for(Customer item:items){
                                System.out.println(item.toString());
                            }
        };
    }

}
