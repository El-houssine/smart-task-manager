# Smart Task Manager

A Spring Boot backend for managing projects and tasks with JWT authentication, role-based access, real-time updates via WebSocket, and AI-assisted task suggestions.

## Quick Start

### Prerequisites
- Java 17
- Maven 3.9+
- PostgreSQL database
- OpenAI API key

### Environment Configuration
You can use OS environment variables or a `.env` file in the project root (supported by spring-dotenv).

Example `.env`:
```
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=dev
DB_URL=jdbc:postgresql://localhost:5432/smart_task_db
DB_USERNAME=postgres
DB_PASSWORD=root
JWT_SECRET_KEY=base64EncodedSecretKeyHere
JWT_ACCESS_TOKEN_EXPIRATION=900000
JWT_REFRESH_TOKEN_EXPIRATION=604800000
OPENAI_API_KEY=sk-your-key
OPENAI_MODEL=gpt-3.5-turbo
```

### Run Locally
- Start PostgreSQL and ensure credentials match your configuration
- Build and run:
```
mvn spring-boot:run
```
Or package and run:
```
mvn clean package
java -jar target/*.jar
```

### Docker
Build and run with Docker:
```
docker build -t smart-task-manager .
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host:5432/smart_task_db \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=secret \
  -e JWT_SECRET_KEY=base64EncodedSecretKeyHere \
  -e OPENAI_API_KEY=sk-your-key \
  smart-task-manager
```

## APIs and Docs
- Base URL: `http://localhost:8080`
- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/v3/api-docs`

### Key Endpoints
- Auth: `POST /api/auth/login`, `POST /api/auth/register`, `POST /api/auth/refresh`
- Users: `GET /api/users`, `GET /api/users/me`, `PUT /api/users/me`, `POST /api/users/{userId}/roles`
- Projects: `POST /api/projects`, `GET /api/projects`, `GET /api/projects/{id}`, `PUT /api/projects/{id}`, `DELETE /api/projects/{id}`
- Tasks: `GET /api/tasks`, `POST /api/tasks`, `GET /api/tasks/{taskId}`, `PUT /api/tasks/{taskId}`, `DELETE /api/tasks/{taskId}`, `POST /api/tasks/{taskId}/suggestions`

## WebSocket
- STOMP endpoint: `/ws` (SockJS enabled)
- Send updates: `/app/tasks/{taskId}/update`
- Subscribe: `/topic/tasks/{taskId}`

## Architecture and Details
For a full technical overview, diagrams, and detailed specifications, see:
- [ARCHITECTURE.md](./ARCHITECTURE.md)

## Tech Stack
- Java 17, Spring Boot 3.4.2
- Spring Web, Security (JWT), Data JPA, Validation, Actuator
- Spring WebSocket (STOMP)
- MapStruct, Lombok, JJWT, springdoc-openapi
- PostgreSQL, OpenAI Java client

## Notes
- Ensure `JWT_SECRET_KEY` is a strong base64-encoded secret
- AI model can be configured with `OPENAI_MODEL`
