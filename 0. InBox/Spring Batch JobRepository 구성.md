### 날짜 : 2023-03-20 11:15
### 주제 : Spring Batch JobRepository 구성
---
### 태그
* #springbatch, #springbatch자바구성 , #JobRepository, #EnableBatchProcessing, #isolation-level-for-create

### 메모
* **@EnableBatchProcessing**를 사용하면 @JobRepository 제공
* **@JobRepository**는 Spring Batch내의 다양한 영구화된 도메인 객체들(JobExcution, StepExcution)의 기본 CRUD 작업에 사용
	* JobLauncher, Job, Step과 같은 주요 프레임워크 기능에도 사용됨
* Job Repository 구성
```xml
<job-repository 
	id                         = "jobRepository" 
	data-source                = "dataSource" 
	transaction-manager        = "transactionManager" 
	isolation-level-for-create = "SERIALIZABLE" 
	table-prefix               = "BATCH_" 
	max-varchar-length         = "1000"
/>
```
* id를 제외하 나머지 설정은 하지 않아도 기본값을 사용할 수 있음
	* max-varchar-length(varchar의 최대 길이 설정)는 기본 2500임
* isolation-level-for-create은 무엇일까?

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#configuringJobRepository

### 연결문서
- [[Spring Batch 자바 구성]]