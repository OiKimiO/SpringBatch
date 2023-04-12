package io.springbatch.springbatchlecture;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Set;

@RestController
@RequestMapping("/batch")
public class JobController {

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobOperator jobOperator;

    @PostMapping("/start")
    public String start(@RequestBody JobInfo jobInfo) throws NoSuchJobException, JobInstanceAlreadyExistsException, JobParametersInvalidException {
        for(Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext();){
            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            jobOperator.start(job.getName(), "id="+job.getName());
        }
        return "batch 시작";
    }

    @PostMapping("/stop")
    public String stop(@RequestBody JobInfo jobInfo) throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {
        for(Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext();){
            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            System.out.println("jobName : " + job.getName());

            Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(job.getName());
            JobExecution jobExecution = runningJobExecutions.iterator().next();
            // 현재 실중인 Step을 완료한 이후에 중단함
            jobOperator.stop(jobExecution.getId());
        }
        return "batch 중단";
    }

    @PostMapping("/restart")
    public String restart(@RequestBody JobInfo jobInfo) throws NoSuchJobException, NoSuchJobExecutionException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException {

        for(Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext();){
            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            System.out.println("jobName : " + job.getName());
            JobInstance lastJobInstance = jobExplorer.getLastJobInstance(job.getName());
            JobExecution lastJobExecution = jobExplorer.getLastJobExecution(lastJobInstance);

            // Job에서 마지막으로 실패한
            jobOperator.restart(lastJobExecution.getId());
        }

        return "batch 재시작";
    }
}
