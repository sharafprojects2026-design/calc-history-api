# Scientific Calculator – SOA Backend

## Project Overview

**Calc History API** is the backend of an academic mini-project titled **Scientific Calculator with Calculation History**.

The project follows a Service-Oriented Architecture (SOA) and provides two main services:

- **SOAP Web Service:** Handles mathematical calculations.
- **REST API:** Manages user authentication and calculation history.

The backend is integrated with a mobile application developed using Flutter.

## Technologies Used

- Java 8
- Spring Boot 2.7
- Maven
- SOAP / JAX-WS
- REST API / Spring Web
- Spring Data JPA
- H2 Database
- JWT (JSON Web Token)
- BCrypt Password Hashing
- Swagger / OpenAPI

## Project Architecture

The backend is organized into several components:

- **model:** Contains the application's data entities.
- **repository:** Handles database access and data persistence.
- **rest:** Contains REST controllers, including `AuthController` and `HistoriqueController`.
- **security:** Handles application security, JWT authentication, and request filters.
- **SOAP:** Contains the SOAP service components, calculator server, and WSDL publishing configuration.
- **CalcApplication:** Main Spring Boot application entry point.

## SOAP Web Service

The SOAP service provides the following mathematical operations:

- Addition
- Division
- Power
- Square Root
- Factorial

The service is defined using a WSDL contract.

**WSDL Endpoint:**

`http://localhost:8081/soap/calculatrice?wsdl`

## REST API

The REST API provides user authentication and calculation history management.

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Authenticate a user |
| GET | `/historique` | Retrieve calculation history |
| POST | `/historique` | Save a calculation |
| DELETE | `/historique/{id}` | Delete a specific calculation |
| DELETE | `/historique` | Clear calculation history |

### Security

The API uses JWT (JSON Web Token) authentication to protect history management endpoints.

Protected requests must include the following HTTP header:

`Authorization: Bearer <token>`

Each authenticated user can access their own calculation history.

## API Documentation

Interactive API documentation is available through Swagger UI:

`http://localhost:8080/swagger-ui/index.html`

## Testing

The `tests/` directory contains the project's testing files:

- `CalculatriceSOAP-soapui-project.xml`: SOAP service tests using SoapUI.
- `New Collection.postman_collection.json`: REST API test collection using Postman.

## Flutter Mobile Application

The backend is integrated with a Flutter mobile application.

The application uses:

- SOAP for mathematical calculations.
- REST API for authentication and calculation history management.

It also supports local calculations when the application is offline.

## Running the Project

To start the Spring Boot application using Maven:

```bash
mvn spring-boot:run
```

Alternatively, run the `CalcApplication` main class directly from IntelliJ IDEA.

## Author

**Sharaf Abdullah**

Class: DSI-3

Supervisor: JOUINI Fatma