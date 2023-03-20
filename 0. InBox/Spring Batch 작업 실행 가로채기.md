### 날짜 : 2023-03-20 09:44
### 주제 : 작업 실행 가로채기
---
### 태그
* #springbatch, #springbatch작업구성 , #springbatch작업실행가로채기

### 메모
* JobExcution시 custom code를 사용할 수 있도록 다양한 라이프사이클 이벤트 알림을 받을 수 있음
* JobExcution의 실패, 성공에 상관 없이 내용을 확인하고 싶으면 아래의 코드처럼 작성
```java
public void afterJob(JobExecution jobExecution){ 
	if (jobExecution.getStatus() == BatchStatus.COMPLETED ) { 
		//job success 
	} else if (jobExecution.getStatus() == BatchStatus.FAILED) { 
		//job failure 
	} 
}
```
* 이 인터페이스에 해당하는 annotation은 2가지임
	* @BeforeJob
	* @AfterJob
	
### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#configureJob

### 연결문서
- [[Spring Batch 작업구성]]