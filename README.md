# Verify One Spring Boot Endpoint's Validation

This is a small Spring Boot application for manually inspecting and validating a user-registration endpoint. It stores valid users in an H2 in-memory database. The database starts empty and is discarded when the application stops.

## Requirements

- Java 17
- Maven
- IntelliJ IDEA
- Postman for manual requests

## Open and run in IntelliJ IDEA

1. In IntelliJ IDEA, choose **File > Open** and select this `verify-one-endpoint` folder (the folder containing `pom.xml`).
2. Allow IntelliJ IDEA to import the Maven project and download its dependencies.
3. Set the project SDK and Maven runner JDK to Java 17 under **File > Project Structure** and **Settings > Build, Execution, Deployment > Build Tools > Maven**.
4. Open `src/main/java/com/example/verifyendpoint/VerifyOneEndpointApplication.java`.
5. Run the `main` method using the green run icon.

The application starts on `http://localhost:8080`. Confirm startup by seeing a log entry containing `Started VerifyOneEndpointApplication` in the IntelliJ Run window.

## API contract

### POST `/api/users`

Content-Type: `application/json`

Request body:

```json
{
  "name": "Ada Lovelace",
  "email": "ada@example.test"
}
```

Validation rules:

- `name` is required, must not be blank, and must be at most 20 characters.
- `email` is required, must not be blank, and must have a valid email format.

For a valid request, the contract is HTTP `201 Created` with a response containing the generated `id`, `name`, and `email`, for example:

```json
{
  "id": 1,
  "name": "Ada Lovelace",
  "email": "ada@example.test"
}
```

For a request that fails validation, the contract is HTTP `400 Bad Request` with a consistent response such as:

```json
{
  "timestamp": "2026-10-28T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Request validation failed",
  "fieldErrors": {
    "email": "email must be a valid email address"
  }
}
```

The timestamp is generated at runtime. Invalid requests are rejected before the controller saves a user.

### GET `/api/users/count`

Returns the number of users currently stored:

```json
{
  "count": 1
}
```

Use this endpoint to inspect the stored-record count before and after manual POST requests. It is supporting verification functionality; the endpoint under investigation is `POST /api/users`.

## Maven commands

From this folder:

```bash
mvn clean test
```

The project includes ordinary Spring Boot test dependencies but intentionally does not include a validation regression test suite or Postman artifacts.
