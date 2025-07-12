# ClamAV Backend

This module provides the backend services for the ClamAV Monitor project. It is a Java-based application built with Maven.

For Chinese documentation, please refer to <mcfile name="README.zh-CN.md" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor\clamav-backend\README.zh-CN.md"></mcfile>.

## Project Structure

- `pom.xml`: Maven project configuration.
- `src/main/`: Contains the main source code.
- `sql/schema.sql`: Database schema definition.

## Getting Started

1.  **Build the project**:
    ```bash
    mvn clean install
    ```
2.  **Run the application**:
    ```bash
    java -jar target/clamav-backend.jar
    ```
    (Note: The actual command might vary based on the specific application setup and entry point.)

## Database Setup

The `sql/schema.sql` file contains the necessary SQL commands to set up the database schema. You will need to execute these commands on your database server before running the backend.

## Deployment

This backend application can be deployed as a standalone JAR or within a Docker container. Refer to the `Dockerfile` and `bootstrap.sh` for Docker deployment instructions.

## Technology Stack

*   **Framework**: Spring Boot 2.5.4
*   **Data Persistence**: MyBatis-Plus
*   **Database**: MySQL
*   **Security**: Spring Security + JWT
*   **Build Tool**: Maven

## Features Implemented

*   **User Registration**: Endpoint for creating new users.
*   **User Authentication**: JWT-based authentication for securing APIs.
*   **Centralized Exception Handling**: Basic setup for handling authentication errors.
*   **Monitoring**: Integrated with Prometheus and Actuator for application monitoring.

## Setup and Installation

### Prerequisites

*   JDK 1.8 or later
*   Maven 3.2+
*   MySQL 5.7 or later

### 1. Database Setup

1.  Make sure you have a running MySQL instance.
2.  Create a database named `clam_guard`.
3.  Execute the script in `src/main/resources/schema.sql` to create the necessary tables.

### 2. Application Configuration

1.  Open `src/main/resources/application.yml`.
2.  Update the `spring.datasource` properties to match your MySQL connection details (username and password).
3.  Review the `jwt` properties. You can change the secret key and expiration time if needed.

### 3. Running the Application

You can run the application using Maven:

```bash
# Navigate to the clam-backend directory
cd clam-backend

# Run the application
mvn spring-boot:run
```

The application will start on port `8081` by default.

## API Endpoints

### 1. Register a new user

*   **URL**: `/register`
*   **Method**: `POST`
*   **Body**:

    ```json
    {
        "username": "testuser",
        "password": "password123"
    }
    ```

*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**: `"User registered successfully"`

### 2. Authenticate a user

*   **URL**: `/authenticate`
*   **Method**: `POST`
*   **Body**:

    ```json
    {
        "username": "testuser",
        "password": "password123"
    }
    ```

*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**:

        ```json
        {
            "jwttoken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY3MjYwODAwMCwiaWF0IjoxNjcyNTIyNDAwfQ.exampleToken"
        }
        ```

After authenticating, include the `jwttoken` in the `Authorization` header for all subsequent requests to protected endpoints:

`Authorization: Bearer <your_token>`

### 3. Create a new project

*   **URL**: `/api/projects`
*   **Method**: `POST`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Body**:

    ```json
    {
        "name": "My First Project",
        "description": "This is a sample project."
    }
    ```

*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**: The created project object.

### 4. Get all projects for the current user

*   **URL**: `/api/projects`
*   **Method**: `GET`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**: An array of project objects.

### 5. Get a single project by ID

*   **URL**: `/api/projects/{id}`
*   **Method**: `GET`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**: The project object.

### 6. Update a project

*   **URL**: `/api/projects/{id}`
*   **Method**: `PUT`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Body**:

    ```json
    {
        "name": "Updated Project Name",
        "description": "Updated description."
    }
    ```

*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**: The updated project object.

### 7. Delete a project

*   **URL**: `/api/projects/{id}`
*   **Method**: `DELETE`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Success Response**:
    *   **Code**: `204 No Content`

## API Key Management

### 8. Create a new API Key

*   **URL**: `/api/keys`
*   **Method**: `POST`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**: The created API Key object.

### 9. Get all API Keys for the current user

*   **URL**: `/api/keys`
*   **Method**: `GET`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**: An array of API Key objects.

### 10. Update an API Key

*   **URL**: `/api/keys/{id}`
*   **Method**: `PUT`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Body**:

    ```json
    {
        "status": "Revoked"
    }
    ```

*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**: The updated API Key object.

### 11. Delete an API Key

*   **URL**: `/api/keys/{id}`
*   **Method**: `DELETE`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Success Response**:
    *   **Code**: `204 No Content`

## Scan History

### 12. Get Scan History

*   **URL**: `/api/scan-history`
*   **Method**: `GET`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Description**: Retrieves the scan history for the authenticated user. Can be filtered by status and date range.
*   **Query Parameters**:
    *   `status` (optional): Filter by scan status (e.g., `Clean`, `Infected`).
    *   `startDate` (optional): Start date for the date range filter (ISO 8601 format, e.g., `2023-01-01T00:00:00.000Z`).
    *   `endDate` (optional): End date for the date range filter (ISO 8601 format, e.g., `2023-12-31T23:59:59.999Z`).
*   **Success Response**:
    *   **Code**: `200 OK`
    *   **Body**: An array of scan history objects.

    ```json
    [
      {
        "id": 1,
        "fileId": "file-123",
        "filename": "document.pdf",
        "status": "Clean",
        "virusName": null,
        "userId": 1,
        "scannedAt": "2023-10-27T10:00:00.000+00:00"
      }
    ]
    ```
    
docker部署clamav
```yaml
    docker run -d --name clamav -p 3310:3310 -v /d/clamav/config:/etc/clamav -v /d/clamav/db:/var/lib/clamav clamav/clamav:latest
```