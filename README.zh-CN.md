# ClamAV 监控系统

这是一个全面的项目，旨在监控 ClamAV。它由以下几个模块组成：

- `clamav-backend`：后端服务，可能是一个 Java 应用程序。
- `clamav-guard`：主前端应用程序，使用 Vue.js 和 TypeScript 构建。
- `clamav-ui`：一个更简单的 UI 模块，使用 HTML、CSS 和 JavaScript 构建。
- `clamav-docs`：项目文档。

## 快速开始

要使用 Docker Compose 设置和运行整个项目，请按照以下步骤操作：

1.  **确保您的系统上已安装 Docker 和 Docker Compose**。

2.  **导航到项目根目录**：
    ```bash
    cd clamav-monitor
    ```

3.  **构建并启动服务**：
    ```bash
    docker-compose up --build
    ```

    此命令将：
    - 构建 `clamav-backend`、`clamav-guard` 和 `clamav-ui` 的 Docker 镜像（如果存在 `Dockerfile` 并已配置）。
    - 根据 `docker-compose.yml` 启动所有定义的服务（后端、前端、数据库等）。

4.  **访问应用程序**：
    - `clamav-guard`（主前端）通常可在 `http://localhost:5173` 访问（或 `docker-compose.yml` 中配置的其他端口）。
    - `clamav-ui`（简单 UI）通常可在 `http://localhost:80` 访问（或 `docker-compose.yml` 中配置的其他端口）。
    - `clamav-backend` 将在 Docker 网络内部运行。

## 项目结构

- <mcfolder name="clamav-backend" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor/clamav-backend\"></mcfolder>: 后端服务。
- <mcfolder name="clamav-docs" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor/clamav-docs\"></mcfolder>: 项目文档。
- <mcfolder name="clamav-guard" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor/clamav-guard\"></mcfolder>: 主前端应用程序。
- <mcfolder name="clamav-ui" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor/clamav-ui\"></mcfolder>: 简单 UI 模块。
- `docker-compose.yml`: Docker Compose 多服务部署配置。