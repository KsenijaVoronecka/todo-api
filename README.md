# Todo API

A RESTful API for task management built with Java and Spring Boot.

## Tech Stack

- **Java 18** — programming language
- **Spring Boot 4.0** — application framework
- **Spring Data JPA** — database access layer
- **PostgreSQL** — relational database
- **Lombok** — boilerplate code reduction
- **Maven** — dependency management

## Features

- Create, read, update and delete tasks (CRUD)
- Filter tasks by status (TODO, IN_PROGRESS, DONE)
- Input validation with meaningful error messages
- Custom exception handling with proper HTTP status codes
- DTO pattern separating API layer from database layer
- Layered architecture (Controller → Service → Repository)

## Project Structure

```
src/main/java/com/ksenija/todo_api/
├── controller/
│   └── TaskController.java       # REST endpoints
├── service/
│   └── TaskService.java          # Business logic
├── repository/
│   └── TaskRepository.java       # Database access
├── model/
│   ├── Task.java                 # Task entity
│   └── TaskStatus.java           # Status enum (TODO, IN_PROGRESS, DONE)
├── dto/
│   ├── TaskRequest.java          # Incoming request object
│   ├── TaskResponse.java         # Outgoing response object
│   └── TaskMapper.java           # Converts between entity and DTO
├── exception/
│   ├── TaskNotFoundException.java
│   └── GlobalExceptionHandler.java
└── config/                       # Security configuration (in progress)
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks?status=TODO` | Get tasks filtered by status |
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create a new task |
| PUT | `/api/tasks/{id}` | Update an existing task |
| DELETE | `/api/tasks/{id}` | Delete a task |

## Request & Response Examples

**Create a task — POST /api/tasks**

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

**Validation error — POST with empty title**

Response — 400 Bad Request:
```json
{
    "title": "Title cannot be blank"
}
```

**Not found — GET /api/tasks/999**

Response — 404 Not Found:
```json
{
    "error": "Task not found: 999"
}
```

## Getting Started

### Prerequisites

- Java 18+
- PostgreSQL 18
- Maven 3.8+

### Database Setup

1. Install PostgreSQL and create a database:

```sql
CREATE DATABASE tododb;
```

2. Update `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tododb
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### Running the Application

```bash
# Clone the repository
git clone https://github.com/KsenijaVoronecka/todo-api.git
cd todo-api

# Run with Maven
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.

Table `tasks` will be created automatically by Hibernate on first run.

### Testing the API

You can test the endpoints using [Postman](https://www.postman.com/) or any HTTP client.

## Error Handling

The API returns consistent error responses:

| Status | Meaning |
|--------|---------|
| 200 OK | Request successful |
| 201 Created | Resource created successfully |
| 204 No Content | Resource deleted successfully |
| 400 Bad Request | Invalid input data |
| 404 Not Found | Resource not found |

## What's Coming Next

- JWT authentication and authorization
- JUnit tests for service layer
- Docker Compose setup
- Deploy to Railway

## Author

Ksenija Voronecka
[GitHub](https://github.com/KsenijaVoronecka/todo-api)
