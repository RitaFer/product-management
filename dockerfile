FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/product-management-0.0.1-SNAPSHOT.jar app.jar

COPY src/main/resources/application.yaml /app/application.yaml

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.config.location=file:/app/application.yaml", "-jar", "app.jar"]