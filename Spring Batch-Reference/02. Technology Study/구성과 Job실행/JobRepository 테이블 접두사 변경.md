### 날짜 : 2023-03-20 15:49
### 주제 : Spring Batch JobRepository 테이블 접두사 변경
---
### 태그
*  #springbatch, #springbatch자바구성 , #JobRepository, #테이블접두사

### 메모
* JobRepository의 변경 가능한 속성 중 하나인 메타 데이터 테이블의 테이블 접두사(Table Prefix)에 대한 설명
	* 접두사는 기본적으로 항상 **BATCH_** 로 되어 있음
	* 필요에 따라 접두사를 변경할 때는 2가지 상황이 있음
		* 스키마 이름을 추가하는 경우 
		* 여러 세트의 메타데이터 테이블이 필요한 경우
```java
	@Configuration @EnableBatchProcessing(tablePrefix = "SYSTEM.TEST_") 
	public class MyJobConfiguration { 
		// job definition 
	}
```
* 위의 코드와 같이 적용하면 메타데이터 테이블에 대한 모든 쿼리는 "SYSTEM.TEST_"가 적용

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#repositoryTablePrefix

### 연결문서
- [[JobRepository 구성]]