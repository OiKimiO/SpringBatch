### 날짜 : 2023-03-20 08:57
### 주제 : Spring Batch 비즈니스 시나리오
---
### 태그
* #springbatch , #springbatch사용 , #springbatch비즈니스, #springbatch시나리오 

### 메모
* Spring Batch가 지원하는 비즈니스 시나리오
	* 배치 **프로세스**를 주기적으로 커밋
	* 동시 일괄 처리 ( 작업의 병렬 처리 )
		* 쓰레드로 일괄 처리를 하는 것인가?
	* 단계적 엔터프라이즈 메시지 기반 처리
	* 대규모 병렬 일괄 처리
	* 실패 후 수동 또는 예약된 다시 시작
	* 종속 단계의 순차적 처리(워크플로 기반 배치로 확장)
	* 부분처리
		* 레코드 건너뛰기(예 : 롤백시)
	* 배치 크기가 작거나 기존 저장 프로시저 또는 스크립트가 있는 경우 전체 배치 트랜잭션

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#spring-batch-intro

### 연결문서
- [[Spring Batch 사용 시나리오]]