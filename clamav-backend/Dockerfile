# 构建阶段
FROM maven:3.8.5-openjdk-8 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 运行阶段
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/target/clamav-backend-1.0.0-release.jar app.jar
EXPOSE 8088
COPY bootstrap.sh .
RUN chmod +x bootstrap.sh
ENTRYPOINT ["./bootstrap.sh"]
