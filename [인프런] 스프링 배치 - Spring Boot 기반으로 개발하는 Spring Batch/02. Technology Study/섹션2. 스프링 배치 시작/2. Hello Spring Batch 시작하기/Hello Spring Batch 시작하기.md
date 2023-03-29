### 날짜 : 2023-03-29 11:23
### 주제 : Hello Spring Batch 시작하기
---
### 태그
* 

### 메모
* @Configuration 선언
	* 하나의 배치 Job을 정의하고 빈 설정

* JobBuilderFactory
	* Job을 생성하는 빌더 팩토리
	* get("helloJob")
		* Job 객체를 생성

* StepBuilderFactory
	* Step을 생성하는 빌더 팩토리
	* get("helloStep")
		* Step 객체를 생성
* Job
	* helloJob 이름으로 Job 생성

* Step
	* helloStep 이름으로 step 구성

* tasklet
	* Step안에서 단인 태스트로 수행되는 로직 구현

* 구동 순서
	* Job 구동 > Step 실행 > Tasklet 실행![[Pasted image 20230329113141.png]]
	
### 출처(참고문헌)
-  

### 연결문서
- 