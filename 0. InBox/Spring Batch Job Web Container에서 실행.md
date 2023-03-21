### 날짜 : 2023-03-21 11:21
### 주제 : Spring Batch Job Web Container에서 실행
---
### 태그
* #springbatch, #springbatch자바구성, #JobLauncher, #JobWebContainer

### 메모
* Web Container에서 Job을 실행하는 방식은 Job Launcher를 통해 Job을 비동기 방식으로 진행하고, 즉시 JobExecution을 반환
	* Job Launcher가 비동기식으로 진행되기에 Job이 재시작될 경우 문제가 발생될 수 있음
	* 이러한 문제를 피하기위해 Job의 상태를 확인하고 적절한 시점에 재시작하는 로직을 구현해야 함
		* @AfterJob은 Job이 종료된 시점에 실행됨으로 JobLauncher와 독립적으로 실행
		* 따라서 @AfterJob으로는 재시작 로직을 짜면 안됨
		* 재시작 로직은 다음에 한번 찾아보기
* ![[Pasted image 20230321143948.png]]

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#runningJobsFromWebContainer

### 연결문서
- [[Spring Batch Job 실행]]
- [[Spring Batch 재시작 가능성]]