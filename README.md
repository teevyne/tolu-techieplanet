# PART ONE - Programming and Algorithm

## Question One – Time in Words

### Filename
QuestionOne.java

### Description
Converts a given time (hour and minute) into words, e.g., 5:47 → "thirteen minutes to six"

### Run:
```java
cd src/main/java/com/assessment/techieplanet/programmingandalgorithm
javac QuestionOne.java
java QuestionOne.java
```
You'll be prompted to enter an hour and minute from the console. Go ahead and enter the hour, press enter and enter the minute and press enter again.


## Question Two – Remove Duplicates in a 2D Array

### Filename
QuestionTwo.java

### Description
Replaces duplicate values in each row of a 2D array with 0, preserving only the first occurrence per row.

### Run:
```declarative
cd src/main/java/com/assessment/techieplanet/programmingandalgorithm
javac QuestionTwo.java
java QuestionTwo.java
```

## Question Three – Sum of Digits & Digital Root

### Filename
QuestionThree.java

### Description:

sumDigits: Recursively sums the digits of a string.
 
digitalRoot: Repeatedly sums digits until a single-digit result remains.

### Run

```declarative
cd src/main/java/com/assessment/techieplanet/programmingandalgorithm
javac QuestionThree.java
java QuestionThree.java
```

# PART THREE -  Student Score Reporting System

A backend application that accepts student scores in 5 subjects, validates them, stores them in a PostgreSQL database, and provides paginated, filterable reports with calculated mean, median, and mode for each student.

# Features
✅ Add multiple student scores with validation (0–100 per subject)

✅ Compute statistical data: mean, median, mode

✅ Paginated and filterable report endpoint

✅ REST API with Swagger UI

✅ Integration & unit tests with Testcontainers

✅ Containerized with Docker Compose

# Tech Stack

✅ Java 17

✅  Spring Boot 3

✅   PostgreSQL

✅   Spring Data JPA

✅   Swagger/OpenAPI

✅   Docker & Docker Compose

✅   JUnit 5 + Testcontainers

# Setup Instructions
## Prerequisites

✅   Docker & Docker Compose installed

✅   Java 17+

✅   Maven 3.8+

### Running with Docker Compose
```declarative 
docker-compose up --build
```

App runs on: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui.html

### Running Tests
```declarative
mvn clean test
```
### API Endpoints
| Method | Endpoint                | Description                                   |
| ------ | ----------------------- | --------------------------------------------- |
| `POST` | `/api/v1/scores`        | Add scores for multiple students              |
| `GET`  | `/api/v1/scores/report` | Get paginated student report (with filtering) |

### Sample Request: POST /api/v1/scores
```json
[
  {
    "studentName": "John Doe",
    "scores": {
      "Mathematics": 85,
      "English": 90,
      "Science": 88,
      "History": 92,
      "Art": 87
    }
  }
]
```

### Sample Response: GET /api/v1/scores/report
```json
{
  "code": 200,
  "message": "Fetched report for students",
  "status": true,
  "data": {
    "content": [
      {
        "studentName": "John Doe",
        "meanScore": 88.4,
        "medianScore": 88,
        "modeScore": 85,
        "subjectScores": {
          "Mathematics": 85,
          "English": 90,
          "Science": 88,
          "History": 92,
          "Art": 87
        }
      }
    ],
    "totalPages": 1,
    "totalElements": 1
  }
}
```

