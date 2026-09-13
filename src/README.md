# SafeWalk City

## Overview

**SafeWalk City** is a city-wide safety escort coordination system designed to help people request an escort when travelling, particularly during late hours or situations where they may feel unsafe.

The system connects users who need an escort with available volunteers. Users can create escort requests by providing their pickup location, destination, date, time, and additional notes. Volunteers can then accept available requests and be assigned to escort users.

The project was developed as a **Spring Boot REST API application** using a MySQL database.

---

## Objectives

The main objectives of SafeWalk City are to:

* Provide users with a convenient way to request a safety escort.
* Connect users with available volunteers.
* Allow users to specify their pickup and destination locations.
* Manage escort requests digitally.
* Track the status of escort requests.
* Allow volunteers to accept escort requests.
* Improve safety and coordination within the city.
* Provide a foundation that can be expanded with additional safety features.

---

## Main Features

### User Management

* User registration
* User authentication
* Secure password storage using BCrypt
* User roles, including regular users and volunteers

### Escort Requests

Users can create an escort request containing:

* Pickup location
* Destination
* Date
* Time
* Additional notes
* Request status

### Escort Assignment

Volunteers can be assigned to escort requests and accept available requests.

### Request Status Management

Escort requests can have different statuses, such as:

* `PENDING`
* `ACCEPTED`
* `CANCELLED`

The status allows the system to track the current state of an escort request.

### Security

The application uses **Spring Security** to protect secured resources.

Passwords are encrypted using **BCrypt** rather than being stored as plain text.

---

## Technologies Used

| Technology      | Purpose                                       |
| --------------- | --------------------------------------------- |
| Java            | Programming language                          |
| Spring Boot     | Backend framework                             |
| Spring Security | Authentication and authorization              |
| Spring Data JPA | Database access and ORM                       |
| MySQL           | Relational database                           |
| Maven           | Dependency and project management             |
| Postman         | API testing                                   |
| GitHub          | Version control                               |
| REST API        | Communication between clients and the backend |

---

## Project Architecture

SafeWalk City follows a layered Spring Boot architecture.

```text
                    ┌──────────────────┐
                    │     Client       │
                    │   / Postman      │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │   Controllers    │
                    │  REST Endpoints  │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │    Services      │
                    │ Business Logic   │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │   Repositories   │
                    │   Data Access    │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │      MySQL       │
                    │    Database      │
                    └──────────────────┘
```

---

## Project Structure

The main Java package is:

```text
com.safewalk.demo
```

The project is organized into different layers:

```text
src/
└── main/
    └── java/
        └── com/
            └── safewalk/
                └── demo/
                    ├── controller/
                    ├── model/
                    ├── repository/
                    ├── service/
                    └── config/
```

### Controller

The controller layer receives HTTP requests and provides REST API endpoints.

Examples include:

* `UserController`
* `TranscriptionController` *(if retained in the current project structure)*
* Escort-related controllers
* Assignment-related controllers

### Model

The model layer represents the application's entities and database tables.

An important entity is:

```text
EscortRequest
```

The `EscortRequest` entity contains information such as:

```text
id
user
pickupLocation
destination
date
time
status
notes
```

### Repository

The repository layer communicates with the MySQL database using Spring Data JPA.

### Service

The service layer contains the application's business logic and coordinates operations between controllers and repositories.

### Configuration

Security-related configuration is handled using Spring Security.

---

## Database

SafeWalk City uses **MySQL** as its relational database.

Important database entities include:

```text
users
escort_requests
escort_assignments
```

The relationship between the main entities can be represented conceptually as:

```text
User
  │
  │ creates
  ▼
EscortRequest
  │
  │ assigned through
  ▼
EscortAssignment
  │
  │ assigned to
  ▼
Volunteer
```

---

## Authentication and Security

The application uses **Spring Security** to protect secured resources.

The security configuration allows authentication-related endpoints to be accessed without authentication while requiring authentication for protected application resources.

Passwords are protected using:

```text
BCryptPasswordEncoder
```

CSRF protection is disabled for the REST API configuration.

---

## REST API

The application exposes REST endpoints that allow clients such as Postman or a frontend application to communicate with the backend.

One of the assignment endpoints implemented in the system is:

```text
POST /api/escort-assignments/accept/{requestId}/{escortId}
```

This endpoint allows a volunteer to accept an escort request.

Example:

```text
POST /api/escort-assignments/accept/2/1
```

where:

```text
2 = Escort Request ID
1 = Volunteer/Escort ID
```

The exact available endpoints depend on the current controller implementation.

---

## Example Escort Request

An escort request can contain information similar to:

```json
{
  "pickupLocation": "Molyko",
  "destination": "Great Soppo",
  "date": "2026-08-20",
  "time": "20:00",
  "notes": "Please provide an escort after evening classes."
}
```

A successful request may subsequently have a status such as:

```text
PENDING
```

and, after being accepted:

```text
ACCEPTED
```

A request that is cancelled will have:

```text
CANCELLED
```

---

## Running the Project

### Prerequisites

Before running SafeWalk City, install:

* Java JDK
* Maven
* MySQL
* Git
* Postman *(for API testing)*

### 1. Clone the Repository

```bash
git clone <repository-url>
```

Navigate into the project:

```bash
cd demo
```

### 2. Configure MySQL

Create a MySQL database for the application.

The database connection should be configured in the Spring Boot application configuration.

For example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/safewalk
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

**Do not commit real database passwords or other credentials to GitHub.**

### 3. Build the Project

Using Maven:

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Alternatively, run the Spring Boot application directly from the IDE.

---

## API Testing

The backend can be tested using **Postman**.

Testing should include:

### Valid Tests

* Register a user
* Login
* Create an escort request
* View escort requests
* Accept an available escort request
* Cancel an escort request

### Invalid Tests

The system should also be tested using invalid scenarios, such as:

* Invalid user credentials
* Missing required fields
* Invalid request IDs
* Invalid volunteer IDs
* Attempting to accept a cancelled request
* Attempting to accept an already accepted request
* Unauthorized access to protected endpoints

The purpose of invalid testing is to verify that the system does not perform operations that should not be allowed.

---

## HTTP Status Codes

The API uses HTTP status codes to communicate the result of requests.

| Status Code                 | Meaning                                        |
| --------------------------- | ---------------------------------------------- |
| `200 OK`                    | Request was successfully processed             |
| `201 Created`               | A new resource was successfully created        |
| `400 Bad Request`           | Invalid request data                           |
| `401 Unauthorized`          | Authentication is required or failed           |
| `403 Forbidden`             | Access to the requested operation is forbidden |
| `404 Not Found`             | Requested resource does not exist              |
| `500 Internal Server Error` | Unexpected server-side error                   |

---

## Software Engineering Design

SafeWalk City was designed using common software engineering principles and UML models.

The system documentation includes:

* System architecture
* Use case diagram
* Class diagram
* Sequence diagrams
* Activity diagrams
* Deployment diagram
* Database design
* REST API design

These diagrams help describe both the static structure and dynamic behaviour of the system.

---

## Scrum Methodology

The project was developed using the **Scrum framework**.

The development process was divided into sprints, with functionality progressively implemented and tested.

### Scrum Team

The team consists of two members.

| Role          | Responsibilities                                                                                                                           |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------------ |
| Scrum Master  | Facilitates Scrum activities, monitors progress, helps identify and remove obstacles, and supports effective team collaboration.           |
| Product Owner | Defines and prioritizes requirements, represents the product objectives, and ensures that the developed features meet the project's goals. |

### Daily Stand-ups

The team conducted short daily stand-up meetings lasting a maximum of **10 minutes**.

The meetings focused on:

* What had already been completed
* What needed to be done next
* Whether progress had been made
* Any issues or obstacles affecting development

---

## Version Control

Git and GitHub were used for source-code management.

Development work was performed using branches rather than making changes directly to the main branch.

The workflow generally follows:

```text
Create / use branch
       ↓
Develop feature
       ↓
Test feature
       ↓
Commit changes
       ↓
Push branch
       ↓
Create Pull Request
       ↓
Review
       ↓
Merge into main
```

---

## Testing

Testing was performed during development using Postman to verify the REST API.

Both successful and unsuccessful requests were tested to ensure that:

* Valid operations work correctly.
* Invalid requests are rejected.
* Authentication is enforced.
* User roles are respected.
* Escort request statuses are handled correctly.
* Escort assignments are created correctly.
* Cancelled requests cannot incorrectly proceed through restricted operations.

---

## Project Status

SafeWalk City currently provides the foundation for a city-wide escort coordination system, including user management, escort requests, volunteer assignment, authentication, database persistence, and REST API functionality.

Future improvements could include:

* Real-time notifications
* GPS/location services
* Live escort tracking
* Emergency/SOS functionality
* Mobile application
* Admin dashboard
* Volunteer availability management
* Rating and feedback system
* Improved authorization and role management
* Deployment to a production server

---

## Future Vision

SafeWalk City can be expanded from a basic escort coordination system into a broader city safety platform.

The long-term vision is to provide users with a reliable digital platform through which they can request assistance, connect with trusted volunteers, and receive support when travelling in potentially unsafe situations.

---

## Authors

**SafeWalk City Development Team**

A two-member Scrum team consisting of:

* Scrum Master
* Product Owner

---

## License

This project was developed for academic/educational purposes.
