package io.springbatch.springbatchlecture;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

// Spring 초기화 후 ApplicationRunner라는 클래스의 메서드를 가장 먼저 호출
// @Component
// public class JobRunner implements ApplicationRunner {
public class JobRunner{
    // Spring 초기화할 때 JobLauncher가 실행됨
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;
/*
    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addString("name", "user2")
                .toJobParameters();

        jobLauncher.run(job,jobParameters);
    }*/
}
