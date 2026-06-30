# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Run stage ----
FROM eclipse-temurin:21-jdk-alpine
RUN addgroup --system --gid 1000 appuser && \
    adduser --system --uid 1000 --gid 1000 appuser
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Dummy defaults – safe to commit
ENV SPRING_PROFILES_ACTIVE=prod \
    SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mydb \
    SPRING_DATASOURCE_USERNAME=changeme \
    SPRING_DATASOURCE_PASSWORD=changeme \
    JWT_SECRET=change-this-secret

EXPOSE 8080
USER appuser
ENTRYPOINT ["java", "-jar", "app.jar"]