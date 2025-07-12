# ClamAV 后端

此模块为 ClamAV 监控项目提供后端服务。它是一个使用 Maven 构建的 Java 应用程序。

## 项目结构

- `pom.xml`: Maven 项目配置。
- `src/main/`: 包含主要源代码。
- `sql/schema.sql`: 数据库模式定义。

## 快速开始

1.  **构建项目**：
    ```bash
    mvn clean install
    ```
2.  **运行应用程序**：
    ```bash
    java -jar target/clamav-backend.jar
    ```
    （注意：实际命令可能因特定应用程序设置和入口点而异。）

## 数据库设置

`sql/schema.sql` 文件包含设置数据库模式所需的 SQL 命令。您需要在运行后端之前在数据库服务器上执行这些命令。

## 部署

此后端应用程序可以部署为独立的 JAR 或 Docker 容器。有关 Docker 部署说明，请参阅 `Dockerfile` 和 `bootstrap.sh`。

## 技术栈

*   **框架**：Spring Boot 2.5.4
*   **数据持久化**：MyBatis-Plus
*   **数据库**：MySQL
*   **安全**：Spring Security + JWT
*   **构建工具**：Maven

## 已实现功能

*   **用户注册**：创建新用户的端点。
*   **用户认证**：基于 JWT 的认证，用于保护 API。
*   **集中式异常处理**：处理认证错误的基本设置。
*   **监控**：与 Prometheus 和 Actuator 集成，用于应用程序监控。

## 设置和安装

### 先决条件

*   JDK 1.8 或更高版本
*   Maven 3.2+ 或更高版本
*   MySQL 5.7 或更高版本

### 1. 数据库设置

1.  确保您有一个正在运行的 MySQL 实例。
2.  创建一个名为 `clam_guard` 的数据库。
3.  执行 `src/main/resources/schema.sql` 中的脚本以创建必要的表。

### 2. 应用程序配置

1.  打开 `src/main/resources/application.yml`。
2.  更新 `spring.datasource` 属性以匹配您的 MySQL 连接详细信息（用户名和密码）。
3.  检查 `jwt` 属性。如果需要，可以更改密钥和过期时间。

### 3. 运行应用程序

您可以使用 Maven 运行应用程序：

```bash
# 导航到 clam-backend 目录
cd clam-backend

# 运行应用程序
mvn spring-boot:run
```

应用程序将默认在 `8081` 端口启动。

## API 端点

### 1. 注册新用户

*   **URL**：`/register`
*   **方法**：`POST`
*   **Body**：

    ```json
    {
        "username": "testuser",
        "password": "password123"
    }
    ```

*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：`"User registered successfully"`

### 2. 认证用户

*   **URL**：`/authenticate`
*   **方法**：`POST`
*   **Body**：

    ```json
    {
        "username": "testuser",
        "password": "password123"
    }
    ```

*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：

        ```json
        {
            "jwttoken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY3MjYwODAwMCwiaWF0IjoxNjcyNTIyNDAwfQ.exampleToken"
        }
        ```

认证后，所有后续对受保护端点的请求，请在 `Authorization` 标头中包含 `jwttoken`：

`Authorization: Bearer <your_token>`

### 3. 创建新项目

*   **URL**：`/api/projects`
*   **方法**：`POST`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **Body**：

    ```json
    {
        "name": "My First Project",
        "description": "This is a sample project."
    }
    ```

*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：创建的项目对象。

### 4. 获取当前用户的所有项目

*   **URL**：`/api/projects`
*   **方法**：`GET`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：项目对象数组。

### 5. 按 ID 获取单个项目

*   **URL**：`/api/projects/{id}`
*   **方法**：`GET`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：项目对象。

### 6. 更新项目

*   **URL**：`/api/projects/{id}`
*   **方法**：`PUT`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **Body**：

    ```json
    {
        "name": "Updated Project Name",
        "description": "Updated description."
    }
    ```

*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：更新的项目对象。

### 7. 删除项目

*   **URL**：`/api/projects/{id}`
*   **方法**：`DELETE`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **成功响应**：
    *   **Code**：`204 No Content`

## API 密钥管理

### 8. 创建新 API 密钥

*   **URL**：`/api/keys`
*   **方法**：`POST`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：创建的 API 密钥对象。

### 9. 获取当前用户的所有 API 密钥

*   **URL**：`/api/keys`
*   **方法**：`GET`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：API 密钥对象数组。

### 10. 更新 API 密钥

*   **URL**：`/api/keys/{id}`
*   **方法**：`PUT`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **Body**：

    ```json
    {
        "status": "Revoked"
    }
    ```

*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：更新的 API 密钥对象。

### 11. 删除 API 密钥

*   **URL**：`/api/keys/{id}`
*   **方法**：`DELETE`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **成功响应**：
    *   **Code**：`204 No Content`

## 扫描历史

### 12. 获取扫描历史

*   **URL**：`/api/scan-history`
*   **方法**：`GET`
*   **Headers**：`Authorization: Bearer <your_token>`
*   **描述**：检索认证用户的扫描历史。可以通过状态和日期范围进行过滤。
*   **查询参数**：
    *   `status` (可选)：按扫描状态过滤（例如，`Clean`，`Infected`）。
    *   `startDate` (可选)：日期范围过滤的开始日期（ISO 8601 格式，例如，`2023-01-01T00:00:00.000Z`）。
    *   `endDate` (可选)：日期范围过滤的结束日期（ISO 8601 格式，例如，`2023-12-31T23:59:59.999Z`）。
*   **成功响应**：
    *   **Code**：`200 OK`
    *   **Body**：扫描历史对象数组。

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
    
### docker部署clamav
```yaml
    docker run -d --name clamav -p 3310:3310 -v /d/clamav/config:/etc/clamav -v /d/clamav/db:/var/lib/clamav clamav/clamav:latest
```