FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8088
COPY target/*.jar transaction-service.jar
ENTRYPOINT ["java", "-jar", "/app/transaction-service.jar"]