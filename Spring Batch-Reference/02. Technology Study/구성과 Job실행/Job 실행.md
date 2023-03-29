### 날짜 : 2023-03-21 11:05
### 주제 : Spring Batch Job 실행
---
### 태그
* #springbatch, #springbatch자바구성, #JobLauncher, #Job

### 메모
* Job은 하나 이상의 Step으로 구성되며, 각 Step은 데이터를 읽고 처리하고 쓰는 순서로 동작 
* Job을 실행하기 위해선 JobLauncher와 Job이 필요
* Job을 실행하는 방법
	* CommandLineJobRunner라는 클래스를 이용해 커맨드라인에서 Job을 실행
		* Job의 이름과 파라미터를 전달할 수 있음
	* Spring Boot 이용시, @EnableBatchProcessing 어노테이션을 이용해 자동으로 JobLauncher와 JobRepository를 생성
		* application.properties 파일에 spring.batch.job.names속성으로 실행할 job이름을 지정
	* Quartz와 같은 스케줄러를 사용해 정해진 시간에 Job을 실행
		* QuartzJobBean클래스를 상속받아 executeInternal 메소드에서 JobLauncher와 JobParameters를 이용하여 Job을 실행

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#runningAJob

### 연결문서
- [[JobLauncher 구성]]