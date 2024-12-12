# Auth Development
---
## 개요
- 인증/인가 관련 로직을 회고하며 개발에 적용하기 위한 저장소입니다


---
## 📝 관련 작성글
* [회원가입? 사용자 등록?](https://velog.io/@seculoper235/%EA%B3%A0%EB%AF%BC-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85-%EC%82%AC%EC%9A%A9%EC%9E%90-%EB%93%B1%EB%A1%9D)

* [계정 연동?](https://velog.io/@seculoper235/%EB%8F%84%EB%A9%94%EC%9D%B8-%EC%86%8C%EC%85%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%97%B0%EB%8F%99-%EB%B0%A9%EB%B2%95%EC%9E%91%EC%84%B1%EC%A4%91)

* [리프레시 토큰 전략?](https://velog.io/@seculoper235/%EA%B3%A0%EB%AF%BC-%EB%A6%AC%ED%94%84%EB%A0%88%EC%8B%9C-%ED%86%A0%ED%81%B0-%EC%A0%84%EB%9E%B5)

* [SNS 연동 방법](https://velog.io/@seculoper235/%EB%8F%84%EB%A9%94%EC%9D%B8-%EC%86%8C%EC%85%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%97%B0%EB%8F%99-%EB%B0%A9%EB%B2%95%EC%9E%91%EC%84%B1%EC%A4%91)


---
## 😁 As-Is
* Maven -> Gradle 마이그레이션
  
* 인증 관련 플로우 차트 작성 및 도메인 설계

* JWT 기능 구현(By TDD)

* Refresh Token 전략 구현(By TDD)

* SNS 연동 기능 추가(By TDD)

* SNS 연동 해제 기능 추가


---
## 💡 To-Be

* 함수형 보조 라이브러리 적용


---
## 🔎 사용 스펙
* Language / Framework: Java / SpringBoot

* Database: PostgreSQL, Redis

* ORM: JPA

* DB VCS: Liquibase(https://docs.liquibase.com/home.html)

* Security: Spring Security

* Test: Spring Test(Jupiter, Mockito)

* HTTP Client: Spring RestClient

* JWT: auth0(https://auth0.com/)
