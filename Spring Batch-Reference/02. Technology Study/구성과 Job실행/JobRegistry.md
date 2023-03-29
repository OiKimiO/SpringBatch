### 날짜 : 2023-03-21 09:28
### 주제 : JobRegistry
---
### 태그
* #JobRegistry, #JobLauncher, #registry , #context

### 메모
* @EnableBatchProcessing을 사용하면 JobRegistry가 자동으로 제공되긴 하나 직접구현을 위해서 JobLauncher를 통해 구현해야 함
* Job의 이름과 인스턴스를 매핑하는 역할을 하는 인터페이스
* **JobLocator**라는 부모 **인터페이스**를 상속 받고, **컨텍스트**에서 사용가능한 Job을 추적하고 관리
* **MapJobRegistry**라는 **구현체**를 이용해 **Job을 등록**하거나 **등록 해제**할 수 있음

### 출처(참고문헌)
-  빙챗 - JobRegistry는 Job에 대한 기본정보를 가지고 있는건가?

### 연결문서
- [[JobLauncher 구성]]