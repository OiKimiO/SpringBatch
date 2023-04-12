package io.springbatch.springbatchlecture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {SimpleJobConfiguration.class, TestBatchConfig.class})
public class SimpleJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void simpleJob_test() throws Exception {

        // given
        // Job 파라미터를 생성
        JobParameters jobParameter = new JobParametersBuilder()
                .addString("name","User1")
                .addLong("date",new Date().getTime())
                .toJobParameters();

        // when
        // Job을 실행
        JobExecution jobExecution  = jobLauncherTestUtils.launchJob(jobParameter);
        JobExecution jobExecution1 = jobLauncherTestUtils.launchStep("step1");


        // then
        // Assert.assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
        // Assert.assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);

        StepExecution stepExecution = (StepExecution)((List) (jobExecution1.getStepExecutions())).get(0);
        Assert.assertEquals(stepExecution.getCommitCount(),11);
        Assert.assertEquals(stepExecution.getReadCount(),100);
        Assert.assertEquals(stepExecution.getWriteCount(),100);
    }

    @Before
    public void clear(){
        jdbcTemplate.execute("delete from customer2");
    }

}
