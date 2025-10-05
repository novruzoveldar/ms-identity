
ms-identity

A Spring Boot–based microservice for managing authentication, registration, and token security within a distributed system.  
This service provides RESTful endpoints for user authentication and registration, supports token storage via Redis, and integrates seamlessly with other microservices through Feign clients.  

---

🧭 Table of Contents

1. [Overview](#overview)  
2. [Features](#features)  
3. [Architecture](#architecture)  
4. [Technologies Used](#technologies-used)  
5. [Project Structure](#project-structure)  
6. [Installation](#installation)  
7. [Configuration](#configuration)  
8. [Usage](#usage)  
9. [API Documentation](#api-documentation)  
10. [Troubleshooting](#troubleshooting)  
11. [Contributors](#contributors)  
12. [License](#license)

---

📘 Overview

`ms-identity` acts as a core identity service responsible for authenticating users, issuing tokens, and managing session data across distributed systems.  
It integrates with Redis for token caching and uses Feign clients for inter-service communication.  
This microservice can be easily embedded within a larger ecosystem, handling authentication and security for multiple applications.

---

✨ Features

- **JWT-based Authentication** — Secure login and token issuance.  
- **User Registration** — API endpoints for new user creation and validation.  
- **Redis Caching** — Token and session management using Redis.  
- **Feign Integration** — Outbound service-to-service communication.  
- **Swagger UI** — Interactive REST API documentation.  
- **Spring Security Integration** — Custom filters and security configuration.  
- **Internationalization (i18n)** — Locale support for multilingual systems.

---

🏗️ Architecture


+--------------------+
|     API Client     |
+---------+----------+
          |
          v
+--------------------+
|   ms-identity      |
|--------------------|
| AuthController     |
| RegisterController |
| TokenStorage       |
| RedisConfiguration |
| SecurityConfig     |
+--------------------+
          |
          v
+--------------------+
|  Redis Cache / DB  |
+--------------------+


---

⚙️ Technologies Used

* **Java 17+**
* **Spring Boot 3+**
* **Spring Security**
* **Spring Data Redis**
* **OpenFeign**
* **Swagger / Springdoc**
* **Gradle Build System**

---

📂 Project Structure

```
ms-identity/
 ├── build.gradle
 ├── settings.gradle
 ├── src/
 │   ├── main/
 │   │   ├── java/com/guavapay/
 │   │   │   ├── MsIdentityApplication.java
 │   │   │   ├── controller/
 │   │   │   │   ├── AuthController.java
 │   │   │   │   └── RegisterController.java
 │   │   │   ├── config/
 │   │   │   │   ├── SecurityConfiguration.java
 │   │   │   │   ├── RedisConfiguration.java
 │   │   │   │   ├── SwaggerConfiguration.java
 │   │   │   │   ├── FeignInterceptor.java
 │   │   │   │   └── i18n/
 │   │   │   ├── cache/
 │   │   │   │   └── TokenStorage.java
 │   │   │   └── constants/Constants.java
 │   │   └── resources/
 │   │       ├── application.yml
 │   │       └── messages.properties
 └── gradlew, gradlew.bat, etc.
```

---

🧩 Installation

   Prerequisites

* Java 17 or higher
* Gradle 7+
* Redis server running locally or remotely

Steps

```bash
# 1. Clone the repository
git clone https://github.com/novruzoveldar/ms-identity.git
cd ms-identity

# 2. Build the project
./gradlew clean build

# 3. Run the application
./gradlew bootRun
```

By default, the application runs on **port 8080**.

---

⚙️ Configuration

You can configure the application through the `application.yml` file or environment variables:

```yaml
server:
  port: 8080

spring:
  redis:
    host: localhost
    port: 6379
    password:
  application:
    name: ms-identity

security:
  jwt:
    secret: your-secret-key
    expiration: 3600000
```

Set environment variables to override defaults when deploying to production.

---

🚀 Usage

   Register a User

```bash
POST /api/v1/register
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "strongpassword"
}
```

Authenticate and Get Token

```bash
POST /api/v1/auth
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "strongpassword"
}
```

Response:

```json
{
  "access_token": "jwt-token",
  "expires_in": 3600
}
```

---

🧾 API Documentation

After the application starts, access Swagger UI at:

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

🧰 Troubleshooting

| Issue                      | Possible Cause                  | Solution                              |
| -------------------------- | ------------------------------- | ------------------------------------- |
| `Redis connection refused` | Redis not running or wrong host | Check `application.yml` Redis config  |
| `401 Unauthorized`         | Invalid or expired token        | Re-authenticate and use a valid token |
| `Port already in use`      | Another process using port 8080 | Update port in `application.yml`      |

---

👥 Contributors

* **Eldar Novruzov** – [novruzoveldar](https://github.com/novruzoveldar)

Contributions and pull requests are welcome!

---

🪪 License

This project is licensed under the **MIT License**.
See the [LICENSE](LICENSE) file for details.

---
