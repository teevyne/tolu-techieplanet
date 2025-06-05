# ASSESSMENT SUBMISSION
## Introduction
There were no assumptions in attending to questions in the first two parts - Programming & Algorithm and DB & SQL

However, there were assumptions for the third part - Application Development
The major assumption was that, for a student, there a number of other relationships that would exist for any student, before his subject scores come into play.

Entities like courses, departments, electives, interest, next of kin, interest groups etc, would all belong to a student. Effectively
that would be a 1:1 pr 1:M relationship with a student (depending on the use case). The realisation informed my decision to just put the student name as a field in the student score entity

The decision to just pass the student name meant I did not deal with relationships at this scale; I could keep it simple and still tackle the question well. In real-life systems, relationships would be key in implementing use cases around the provisions of this assessment

This decision also meant I was limited in my attempt at implementing microservices. Ideally, I would have had a `studentservice` service strictly focused on student - entity, repository and services
and then the `scoreservice` would depend on the student service to provides its value.

## Design Decision
The first major design decision was around modelling. Since I was not working with relationships on a large scale, I decided to keep my schemas simple by using a Map to save the subjects in the database as against composing my StudentScore entity with another entity.
It achieved the same result and so, because I did not have a lot of data I was dealing with but in real-life, I believe I would favour composition and relationship mapping over persisting a Map.

Furthermore, I separate domains for ease of access. I worked with a transformer, which has the service injected into it. This transformer service is exposed to the controller and is the class in which all forms of first-level validations will always be carried out before even getting into the regular service layer.
The implementations in the service layer (which ideally will interact with other services across the system) will be made available to other service, which will in turn have their own transformers. 

In addition, I converted all requests into models for the system to work with in-case anything comes up with Hibernate sessions and the request is truncated mid-processing.

The other parts of the assessment were duly and well-answered. The documentation on how to start and test them is provided below.

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

