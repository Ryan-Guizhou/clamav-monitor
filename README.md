# ClamAV Monitor

This is a comprehensive project designed to monitor ClamAV. It consists of several modules:

- `clamav-backend`: The backend service, likely a Java application.
- `clamav-guard`: The main frontend application, built with Vue.js and TypeScript.
- `clamav-ui`: A simpler UI module, built with HTML, CSS, and JavaScript.
- `clamav-docs`: Project documentation.

For Chinese documentation, please refer to <mcfile name="README.zh-CN.md" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor\README.zh-CN.md"></mcfile>.

## Getting Started

To set up and run the entire project using Docker Compose, follow these steps:

1.  **Ensure Docker and Docker Compose are installed** on your system.

2.  **Navigate to the project root directory**:
    ```bash
    cd clamav-monitor
    ```

3.  **Build and start the services**:
    ```bash
    docker-compose up --build
    ```

    This command will:
    - Build the Docker images for `clamav-backend`, `clamav-guard`, and `clamav-ui` (if `Dockerfile`s are present and configured).
    - Start all defined services (backend, frontend, database, etc.) as per `docker-compose.yml`.

4.  **Access the applications**:
    - The `clamav-guard` (main frontend) will typically be available at `http://localhost:5173` (or the port configured in `docker-compose.yml`).
    - The `clamav-ui` (simple UI) will typically be available at `http://localhost:80` (or the port configured in `docker-compose.yml`).
    - The `clamav-backend` will be running internally within the Docker network.

## Project Structure

- <mcfolder name="clamav-backend" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor/clamav-backend\"></mcfolder>: Backend services.
- <mcfolder name="clamav-docs" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor/clamav-docs\"></mcfolder>: Project documentation.
- <mcfolder name="clamav-guard" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor/clamav-guard\"></mcfolder>: Main frontend application.
- <mcfolder name="clamav-ui" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor/clamav-ui\"></mcfolder>: Simple UI module.
- `docker-compose.yml`: Docker Compose configuration for multi-service deployment.