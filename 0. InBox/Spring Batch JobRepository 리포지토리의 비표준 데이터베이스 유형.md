### 날짜 : 2023-03-20 16:13
### 주제 : Spring Batch JobRepository 리포지토리의 비표준 데이터베이스 유형
---
### 태그
* #springbatch, #springbatch자바구성 , #JobRepository

### 메모
* Spring Batch에서 지원되지 않는 데이터베이스 플랫폼을 사용할 경우 그와 유사한 데이터베이스를 등록할 수 있는 기술
* 등록할 데이터베이스와 유사한 데이터베이스를 찾는 코드
```java
@Bean 
public JobRepository jobRepository() throws Exception { 
	JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean(); 
							 factory.setDataSource(dataSource); 
							 factory.setDatabaseType("db2"); 
							 factory.setTransactionManager(transactionManager);
	return factory.getObject(); 
}
```
* JobRepositoryFactoryBean은 Datasource에서 데이터베이스 유형을 자동으로 감지
* RDBMS를 사용하지 않거나 작동하지 않는다면 SimpleJobRepository가 의존하는 다양한 Dao 인터페이스를 구현하고 일반적인 Spring 방식으로 수동 연결이 필요

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#nonStandardDatabaseTypesInRepository

### 연결문서
- [[Spring Batch JobRepository 구성]]