### 날짜 : 2023-03-20 11:34
### 주제 : Spring Batch JobRepository에 대한 Transaction 구성
---
### 태그
* #springbatch, #springbatch자바구성 , #JobRepository, #transaction, #proxy, #Process , #Thread 

### 메모
* 네임스페이스나 FactoryBean을 이용해 Repository 생성
	* 이 경우 자동으로 Transaction Advice 생성됨
		*  Transaction Advice는 Batch Metadata와 같은 상태 정보를 올바르게 저장하기 위해 필요
		
* create* 메서드의 고립수준은 개별적으로 지정
	* 고립 수준은 **SERIALIZABLE**이고 두개의 **프로세스**가 동시에 실행하려고 할 때, 한개의 프로세스만 실행되도록 보장함
		* 트랜잭션 고립수준이 SERIALIZABLE이 아니어도 됨, 그러나 그에 따른 조치는 필요
	* 고립 수준은 상황에 맞게 선택할 수 있으나 왠만하면 안전하게 처리 필요
	* 고립수준이 직렬화라고 해도 **create* 메서드의 실행 시간**이 **매무 짧기** 때문에 문제가 없음 
		* 단순히 **데이터베이스의 데이터 입력을 위한 메서드**이기에 실행시간이 짧은 것
	* 고립수준을 적용하기 위한 코드
	```java
	@Configuration @EnableBatchProcessing(isolationLevelForCreate = "ISOLATION_REPEATABLE_READ") 
	public class MyJobConfiguration { 
		// job definition 
	}
	```

* Java에서 Repository Transaction 동작을 구성하는 코드
```java
@Bean
public TransactionProxyFactoryBean baseProxy() { 
	TransactionProxyFactoryBean transactionProxyFactoryBean = new 
TransactionProxyFactoryBean(); 
	Properties transactionAttributes = new Properties(); 
			   transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");
			    
	transactionProxyFactoryBean.setTransactionAttributes(transactionAttributes); 
	transactionProxyFactoryBean.setTarget(jobRepository()); 
	transactionProxyFactoryBean.setTransactionManager(transactionManager());
	 
	return transactionProxyFactoryBean; 
}
```

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#txConfigForJobRepository

### 연결문서
- [[JobRepository 구성]]