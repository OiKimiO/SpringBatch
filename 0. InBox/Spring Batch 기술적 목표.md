### 날짜 : 2023-03-20 09:03
### 주제 : Spring Batch 기술적 목표
---
### 태그
* #springbatch , #springbatch사용 , #springbatch기술적목표 

### 메모
* 배치 개발자가 Spring 프로그래밍 모델을 사용하도록함
	* **비즈니스 논리에 집중**하고 **프레임워크가 인프라를 관리**
* 인프라, 배치 실행 환경 및 배치 애플리케이션 간 문제를 명확히 구분
* 모든 프로젝트에서 구현할 수 있는 인터페이스로 공통의 핵심 실행 서비스 제공
* **즉시** 사용할 수 있는 핵심 실행 인터페이스의 간단한 기본 구현을 제공
* **모든 계층에서 Spring 프레임워크를 사용**하여 **서비스를 쉽게 구성**, 사용자 지정 및 확장
* **기존의 모든 핵심 서비스**는 **인프라 계층에 영향을 주지 않고** **쉽게 교체하거나 확장**
* Maven을 사용하여 구축된 애플리케이션과 완전히 분리된 아키텍처 JAR로 간단한 배포 모델을 제공

### 출처(참고문헌)
-  https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#spring-batch-intro

### 연결문서
- [[Spring Batch 사용 시나리오]]