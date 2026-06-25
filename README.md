# Todo API

A RESTful API for task management built with Java and Spring Boot, featuring JWT authentication, input validation, and Docker support.

## Tech Stack

- **Java 18** — programming language
- **Spring Boot 4.0** — application framework
- **Spring Security + JWT** — authentication and authorization
- **Spring Data JPA / Hibernate** — database access layer
- **PostgreSQL** — relational database
- **Lombok** — boilerplate code reduction
- **Maven** — dependency management
- **Docker + Docker Compose** — containerization
- **JUnit 5 + Mockito** — unit testing

## Features

- Create, read, update and delete tasks (CRUD)
- Filter tasks by status (TODO, IN_PROGRESS, DONE)
- JWT authentication — register, login, protected endpoints
- Input validation with meaningful error messages
- Custom exception handling with proper HTTP status codes
- DTO pattern separating API layer from database layer
- Layered architecture (Controller → Service → Repository)
- Unit tests for service layer
- Docker Compose for easy local setup

## Project Structure

```
src/main/java/com/ksenija/todo_api/
├── controller/
│   ├── TaskController.java         # Task REST endpoints
│   └── AuthController.java         # Register and login endpoints
├── service/
│   ├── TaskService.java            # Task business logic
│   ├── UserService.java            # User business logic
│   └── JwtService.java             # JWT token operations
├── repository/
│   ├── TaskRepository.java         # Task database access
│   └── UserRepository.java         # User database access
├── model/
│   ├── Task.java                   # Task entity
│   ├── TaskStatus.java             # Status enum (TODO, IN_PROGRESS, DONE)
│   ├── User.java                   # User entity
│   └── Role.java                   # Role enum (USER, ADMIN)
├── dto/
│   ├── TaskRequest.java            # Incoming task request
│   ├── TaskResponse.java           # Outgoing task response
│   ├── TaskMapper.java             # Entity ↔ DTO conversion
│   ├── AuthRequest.java            # Login/register request
│   └── AuthResponse.java           # JWT token response
├── exception/
│   ├── TaskNotFoundException.java
│   └── GlobalExceptionHandler.java
└── config/
    ├── SecurityConfig.java         # Spring Security configuration
    ├── JwtAuthFilter.java          # JWT request filter
    └── PasswordConfig.java         # BCrypt password encoder
```

## API Endpoints

### Authentication (public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### Tasks (requires JWT token)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks?status=TODO` | Get tasks filtered by status |
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create a new task |
| PUT | `/api/tasks/{id}` | Update an existing task |
| DELETE | `/api/tasks/{id}` | Delete a task |

## Request & Response Examples

### Register — POST /api/auth/register

Request body:
```json
{
    "email": "user@example.com",
    "password": "123456"
}
```

Response — 201 Created:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Login — POST /api/auth/login

Request body:
```json
{
    "email": "user@example.com",
    "password": "123456"
}
```

Response — 200 OK:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Create a task — POST /api/tasks

Add the token to the Authorization header:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

Request body:
```json
{
    "title": "Buy groceries",
    "description": "Milk, bread, eggs",
    "status": "TODO",
    "deadline": "31-12-2026 18:00:00"
}
```

Response — 201 Created:
```json
{
    "id": 1,
    "title": "Buy groceries",
    "description": "Milk, bread, eggs",
    "status": "TODO",
    "deadline": "31-12-2026 18:00:00",
    "createdAt": "02-06-2026 16:09:50"
}
```

### Error responses

**Validation error — 400 Bad Request:**
```json
{
    "title": "Title cannot be blank"
}
```

**Not found — 404 Not Found:**
```json
{
    "error": "Task not found: 999"
}
```

**Unauthorized — 401:**
```json
{
    "error": "Full authentication is required to access this resource"
}
```

## Getting Started

### Option 1 — Docker Compose (recommended)

The easiest way to run the project. Requires [Docker Desktop](https://www.docker.com/products/docker-desktop/).

**1. Clone the repository:**
```bash
git clone https://github.com/KsenijaVoronecka/todo-api.git
cd todo-api
```

**2. Copy the environment file and fill in your values:**
```bash
cp .env.example .env
```

**3. Build the JAR file:**
```bash
./mvnw clean package -DskipTests
```

**4. Run with Docker Compose:**
```bash
docker-compose up --build
```

The application will start on `http://localhost:8080`.
PostgreSQL will be available on port `5433`.

**5. Stop the application:**
```bash
docker-compose down
```

---

### Option 2 — Run locally without Docker

**Prerequisites:**
- Java 18+
- PostgreSQL 18
- Maven 3.8+

**1. Create a PostgreSQL database:**
```sql
CREATE DATABASE tododb;
```

**2. Update `src/main/resources/application.properties`:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tododb
spring.datasource.username=postgres
spring.datasource.password=your_password
```

**3. Run the application:**
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.
Tables `tasks` and `users` will be created automatically by Hibernate.

## Testing the API

Use [Postman](https://www.postman.com/) or any HTTP client.

**Workflow:**
1. Register a user via `POST /api/auth/register`
2. Copy the token from the response
3. Add the token to subsequent requests: `Authorization: Bearer <token>`
4. Access protected task endpoints

## Running Tests

```bash
./mvnw test
```

7 unit tests covering all TaskService methods.

## Error Handling

| Status | Meaning |
|--------|---------|
| 200 OK | Request successful |
| 201 Created | Resource created successfully |
| 204 No Content | Resource deleted successfully |
| 400 Bad Request | Invalid input data or wrong date format |
| 401 Unauthorized | Missing or invalid JWT token |
| 404 Not Found | Resource not found |

## Security Notes

- Passwords are hashed using BCrypt — never stored in plain text
- JWT tokens expire after 24 hours
- In production, store `JWT_SECRET` and database credentials as environment variables, never in source code
- `application.properties` contains a default JWT secret for local development only
- In production or Docker, override it via environment variables in `.env` file

## Author

Ksenija Voronecka
[GitHub](https://github.com/KsenijaVoronecka/todo-api)