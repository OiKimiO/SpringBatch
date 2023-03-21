### 날짜 : 2023-03-21 08:56
### 주제 :  JobLauncher 구성
---
### 태그
* #springbatch, #springbatch자바구성, #JobLauncher, #JobRegistry 

### 메모
* **@JobLauncher**는 Job을 관리하는 역할
	* job을 멈추거나 실행하는 역할을 담당
	* JobRegistry를 직접 설정하는 방법
		* JobRegistry는 JobLauncher의 필수 의존성이기에 먼저 직접 설정하는 방법에 대해 설명하고자 함

	* JobLauncher는 Job을 실행하기 위한 Interface임
	* JobLauncher의 구현체는 SimpleJobLauncher임
		* JobRepository를 통해 JobExcution을 가져오고 TaskExecutor를 통해 Job을 실행
		* SimpleJobLauncher는 동기 작업을 기본으로 봄
		![[Pasted image 20230321091741.png]]
		* 동기 작업의 경우 HTTP가 Batch작업이 완료될 때까지 기다리고 있으며 Job에 대한 결과까지 return 받음
		* SimpleJobLauncher를 통해 동기작업을 진행할 경우 HTTP 통신에 좋지 않음
			* 장시간 실행되는 프로세스(배치 작업과 같은)에 필요한 시간 동안 HTTP 요청을 열어두는 것이 좋은 방법이 아니기 때문
			* 인터넷상에 있는 라우터, 로드 밸런서, 프록시 등의 노드들이 연결을 끊거나 무시할수도 있기 때문에 **안정성**을 생각하면 **비동기 방식**으로 **진행**하는게 좋음
		![[Pasted image 20230321091922.png]]
			* 비동기 방식으로 진행할 경우 HTTP는 JobLauncher를 실행하고 종료되고 Job에 대한 결과는 Future객체에 담겨 반환됨

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#configuringJobLauncher

### 연결문서
- [[Spring Batch JobRepository 구성]]