package io.springbatch.springbatchlecture;

import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRepositoryListener implements JobExecutionListener {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();

        JobParameters jobParamters = new JobParametersBuilder().addString("requestDate", "20230405").toJobParameters();

        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParamters);

        if(lastJobExecution != null){
            for(StepExecution execution: lastJobExecution.getStepExecutions()){
                BatchStatus status = execution.getStatus();
                ExitStatus exitStatus = execution.getExitStatus();
                String stepName = execution.getStepName();
                System.out.println("status = " + status);
                System.out.println("exitStatus = " + exitStatus);
                System.out.println("stepName = " + stepName);
            }
        }
    }
}
