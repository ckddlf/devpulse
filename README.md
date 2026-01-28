# Dev-Pulse 🚀

> GitHub 연동 개발자 포트폴리오 및 기술 지표 제공 플랫폼

개발자의 GitHub 레포지토리를 자동으로 분석하여 포트폴리오를 생성하고, 기술 스택을 시각화하는 웹 애플리케이션입니다.

---

## 📋 목차

- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)
- [주요 API 엔드포인트](#-주요-api-엔드포인트)
- [데이터베이스 스키마](#-데이터베이스-스키마)
- [사용 방법](#-사용-방법)
- [개발 로드맵](#-개발-로드맵)

---

## ✨ 주요 기능

### 1. GitHub OAuth 인증
- GitHub 계정으로 간편하게 로그인
- 자동으로 사용자 정보 및 Access Token 저장

### 2. 레포지토리 자동 동기화
- GitHub API를 통해 사용자의 모든 레포지토리 정보 수집
- 레포지토리별 언어 통계 분석 (바이트 수 → 퍼센티지 자동 계산)
- 주기적 동기화 지원

### 3. 포트폴리오 자동 생성
- **프로필 섹션**: 이름, 직책, 위치, 자기소개
- **연락처**: 이메일, 전화번호, GitHub, 블로그, LinkedIn
- **경력**: 회사명, 직책, 기간, 업무 내용 (추가/수정/삭제)
- **학력**: 학교, 학위, 전공, 기간 (추가/수정/삭제)
- **기술 스택**: 프로젝트에서 자동 수집 + 수동 관리
- **프로젝트 카드**: 레포지토리 정보 + 숨김/표시 기능

### 4. 기술 스택 자동 분류
프로젝트에서 사용된 언어를 자동으로 수집하고 카테고리별로 분류:
- **Backend**: Java, Python, JavaScript, Go, Ruby, PHP, C#, Kotlin 등
- **Frontend**: HTML, CSS, React, Vue, Angular, Svelte 등
- **Database**: SQL, MySQL, PostgreSQL, MongoDB, Redis 등
- **DevOps**: Docker, Kubernetes, Shell 등
- **Mobile**: Swift, Dart 등
- **Language**: C, C++ 등
- **Other**: 기타 분류되지 않은 기술

### 5. 프로젝트 관리
- 포트폴리오에서 특정 프로젝트 숨김/표시
- Star, Fork 수 자동 표시
- 기술 스택 비율 시각화

---

## 🛠 기술 스택

### Backend
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Build Tool**: Gradle
- **ORM**: Spring Data JPA (Hibernate)
- **Authentication**: Spring Security OAuth2

### Frontend
- **Template Engine**: Thymeleaf
- **JavaScript**: Vanilla JS (ES6+)
- **CSS**: Custom CSS (Gradient UI)

### Database
- **RDBMS**: MySQL 8.0+
- **JSON Storage**: MySQL JSON 컬럼 타입 활용

### External API
- **GitHub REST API v3**
- **WebClient**: Spring WebFlux (비동기 HTTP 클라이언트)

---

## 📁 프로젝트 구조

```
src/main/java/com/example/devpulse/
├── client/
│   └── GitHubApiClient.java              # GitHub API 호출
├── config/
│   └── SecurityConfig.java               # Spring Security 설정
├── controller/
│   ├── AuthController.java               # 인증 관련 컨트롤러
│   ├── RepositoryController.java         # 레포지토리 API
│   ├── PortfolioController.java          # 포트폴리오 API
│   └── UserController.java               # 사용자 정보 API
├── dto/
│   ├── GitHubRepoDto.java                # GitHub API 응답 DTO
│   ├── GitRepositoryDto.java             # 레포지토리 응답 DTO
│   ├── PortfolioDto.java                 # 포트폴리오 DTO
│   └── UserDto.java                      # 사용자 DTO
├── entity/
│   ├── User.java                         # 사용자 엔티티
│   ├── Repository.java                   # 레포지토리 엔티티
│   ├── TechStack.java                    # 기술 스택 엔티티
│   ├── RepositoryTechStack.java          # 레포-기술 연결 엔티티
│   ├── RepositoryTechStackId.java        # 복합키
│   ├── Portfolio.java                    # 포트폴리오 엔티티
│   ├── ProjectCard.java                  # 프로젝트 카드 엔티티
│   ├── AiAnalysis.java                   # AI 분석 엔티티
│   ├── Subscription.java                 # 구독 엔티티
│   ├── PlanType.java                     # 구독 플랜 Enum
│   └── SubscriptionStatus.java           # 구독 상태 Enum
├── repository/
│   ├── UserRepository.java
│   ├── RepositoryRepository.java
│   ├── TechStackRepository.java
│   ├── RepositoryTechStackRepository.java
│   └── PortfolioRepository.java
├── security/
│   ├── CustomOAuth2User.java             # OAuth2 사용자 정보
│   ├── CustomOAuth2UserService.java      # OAuth2 사용자 로드
│   └── OAuth2AuthenticationSuccessHandler.java
├── service/
│   ├── UserService.java
│   ├── RepositoryService.java
│   └── PortfolioService.java
└── DevPulseApplication.java              # 메인 애플리케이션

src/main/resources/
├── templates/
│   ├── index.html                        # 로그인 페이지
│   ├── success.html                      # 로그인 성공 페이지
│   ├── dashboard.html                    # 대시보드
│   └── portfolio.html                    # 포트폴리오 페이지
└── application.properties                # 애플리케이션 설정
```

---

## 🚀 시작하기

### 1. 사전 요구사항

- Java 17 이상
- MySQL 8.0 이상
- GitHub OAuth App 등록 필요

### 2. GitHub OAuth App 설정

1. GitHub → Settings → Developer settings → OAuth Apps
2. New OAuth App 클릭
3. 설정:
   - **Application name**: Dev-Pulse
   - **Homepage URL**: `http://localhost:8080`
   - **Authorization callback URL**: `http://localhost:8080/login/oauth2/code/github`
4. Client ID와 Client Secret 복사

### 3. 데이터베이스 생성

```sql
CREATE DATABASE devpulse CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 4. application.properties 설정

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/devpulse
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# OAuth2
spring.security.oauth2.client.registration.github.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=YOUR_CLIENT_SECRET
spring.security.oauth2.client.registration.github.scope=read:user,user:email,repo

# Logging
logging.level.com.example.devpulse=DEBUG
```

### 5. 애플리케이션 실행

```bash
# Gradle 빌드
./gradlew build

# 실행
./gradlew bootRun
```

### 6. 접속

브라우저에서 `http://localhost:8080` 접속

---

## 📡 주요 API 엔드포인트

### 인증
- `GET /` - 로그인 페이지
- `GET /login/oauth2/code/github` - GitHub OAuth 콜백
- `GET /dashboard` - 대시보드
- `GET /portfolio` - 포트폴리오 페이지

### 사용자
- `GET /api/user/me` - 현재 로그인한 사용자 정보

### 레포지토리
- `POST /api/repositories/sync` - 레포지토리 동기화
- `GET /api/repositories/my` - 내 레포지토리 목록
- `GET /api/repositories/my/own` - Fork 제외한 내 레포지토리

### 포트폴리오
- `GET /api/portfolio/my` - 내 포트폴리오 조회
- `PUT /api/portfolio/my` - 포트폴리오 업데이트
- `POST /api/portfolio/my/projects/{projectId}/toggle` - 프로젝트 숨김/표시
- `GET /api/portfolio/my/skills/collect` - 프로젝트에서 기술 스택 자동 수집

---

## 🗄 데이터베이스 스키마

### users
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    github_id VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    avatar_url VARCHAR(500),
    bio TEXT,
    github_access_token VARCHAR(500),
    subscription_type VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### repositories
```sql
CREATE TABLE repositories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    github_repo_id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    url VARCHAR(500),
    stars INT DEFAULT 0,
    forks INT DEFAULT 0,
    is_fork BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_synced_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

### tech_stacks
```sql
CREATE TABLE tech_stacks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    category VARCHAR(50),
    icon_url VARCHAR(500)
);
```

### repository_tech_stacks
```sql
CREATE TABLE repository_tech_stacks (
    repository_id BIGINT NOT NULL,
    tech_stack_id BIGINT NOT NULL,
    percentage DECIMAL(5,2),
    bytes BIGINT,
    PRIMARY KEY (repository_id, tech_stack_id),
    FOREIGN KEY (repository_id) REFERENCES repositories(id) ON DELETE CASCADE,
    FOREIGN KEY (tech_stack_id) REFERENCES tech_stacks(id) ON DELETE CASCADE
);
```

### portfolios
```sql
CREATE TABLE portfolios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    display_name VARCHAR(255),
    introduction TEXT,
    job_title VARCHAR(255),
    location VARCHAR(255),
    contact_email VARCHAR(255),
    phone_number VARCHAR(50),
    github_url VARCHAR(500),
    blog_url VARCHAR(500),
    linkedin_url VARCHAR(500),
    personal_website VARCHAR(500),
    experiences JSON,
    educations JSON,
    skills JSON,
    hidden_projects JSON,
    is_public BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

### project_cards
```sql
CREATE TABLE project_cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    repository_id BIGINT UNIQUE NOT NULL,
    title VARCHAR(255),
    summary TEXT,
    readme_content MEDIUMTEXT,
    features JSON,
    tech_stack JSON,
    demo_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (repository_id) REFERENCES repositories(id) ON DELETE CASCADE
);
```

---

## 📖 사용 방법

### 1. 로그인
1. 메인 페이지에서 "GitHub으로 로그인" 클릭
2. GitHub 인증 진행
3. 자동으로 포트폴리오 페이지로 이동

### 2. 레포지토리 동기화
1. 포트폴리오 페이지 하단 "프로젝트" 섹션
2. "🔄 프로젝트 동기화" 버튼 클릭
3. GitHub API를 통해 레포지토리 정보 자동 수집

### 3. 기술 스택 자동 수집
1. "기술 스택" 섹션
2. "🔄 프로젝트에서 수집" 버튼 클릭
3. 모든 레포지토리의 언어 분석
4. 카테고리별로 자동 분류 및 저장

### 4. 포트폴리오 편집
- **소개**: 이름, 직책, 위치, 자기소개 수정
- **연락처**: 이메일, GitHub, 블로그 등 추가
- **경력**: "✏️ 편집" 버튼으로 경력 추가/수정/삭제
- **학력**: 학교, 학위, 전공 정보 관리
- **기술 스택**: 카테고리별 기술 추가/삭제, 레벨 설정

### 5. 프로젝트 관리
- **숨김**: 포트폴리오에 표시하고 싶지 않은 프로젝트 숨기기
- **표시**: 숨긴 프로젝트 다시 표시



## 🔧 트러블슈팅

### LazyInitializationException
- **증상**: techStacks 접근 시 에러
- **해결**: `@EntityGraph(attributePaths = {"techStacks", "techStacks.techStack"})` 사용

### JSON 파싱 오류
- **증상**: Portfolio의 experiences, skills 필드 파싱 실패
- **해결**: ObjectMapper를 사용한 안전한 파싱 구현

### CORS 오류
- **증상**: API 호출 시 CORS 에러
- **해결**: SecurityConfig에서 CORS 설정

---

## 📄 라이선스

이 프로젝트는 학습 및 포트폴리오 목적으로 제작되었습니다.

---

## 👨‍💻 개발자

**ckddlf**
- GitHub: [@ckddlf](https://github.com/ckddlf)

---

**Dev-Pulse** - 개발자의 성장을 시각화하다 🚀
