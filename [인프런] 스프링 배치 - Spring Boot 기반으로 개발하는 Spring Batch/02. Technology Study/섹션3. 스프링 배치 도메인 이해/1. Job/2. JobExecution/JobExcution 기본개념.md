### 날짜 : 2023-04-03 09:30
### 주제 : JobExcution 기본개념
---
### 태그
* #JobExcution

### 메모
* JobInstance에 대한 한번의 시도를 의미하는 객체로 Job 실행 중 발생한 정보들을 저장하고 있는 객체
	* 시작시간, 종료시간, 상태(시작됨, 완료, 실패), 종료상태의 속성을 가짐
	
* JobInstance와의 관계
	* JobInstance : JobExcution = 1 : M
	* JobExcution은 'FAILED' or 'COMPLETE' 등의 Job 실행 결과 상태를 가짐
		* COMPLETE면 JobInstance 실행이 완료된 것으로 간주해 재실행 불가
		* FAILED면 JobInstance 실행이 완료된 것으로 간주하지 않아 재실행 가능
	* JobExcution의 실행 상태가 COMPLETE될 때까지 JobInstancs를 여러번 시도할 수 있음

### 출처(참고문헌)
-  

### 연결문서
- 