### 날짜 : 2023-03-20 09:29
### 주제 : Spring Batch 재시작 가능성
---
### 태그
* #springbatch, #springbatch작업구성 , #springbatch재시작가능성, #jobInstance

### 메모
* 배치 작업 실행시 발생하는 문제 중 하나는 배치 작업의 **재시작** 실행 문제
	* 재시작은 **특정 작업 인스턴스의 실행이 이미 존재하는 경우**를 말함
	* 개발자의 판단하에 새로운 JobInstance 생성 가능
	* JobInstance생성 후 재시작 여부를 boolean으로 설정 가능
		* (true : 재시작, false : 재시작X)
	* 문제 발생 코드
		* 첫번째 JobExcute 이후, 두번째 JobExcute에선 JobRestartException 에러 발생
``` java
	// job = SimpleJob이라고 초기화된 인스턴스
	Job job = new SimpleJob();
		// job의 재시작을 금지(false)
		job.setRestartable(false);
		 
	JobParameters jobParameters = new JobParameters();
	
	//  첫번째 job을 실행
	JobExecution firstExecution = jobRepository.createJobExecution(job, jobParameters); 
	
	jobRepository.saveOrUpdate(firstExecution);
	 
	try { 
		//  두번째 job을 실행시 문제가 발생함
		jobRepository.createJobExecution(job, jobParameters); 
		fail(); 
	} catch (JobRestartException e) { 
		// expected 
	}
``` 

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#restartability

### 연결문서
- [[Spring Batch 작업구성]]