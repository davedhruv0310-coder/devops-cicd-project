# Step 1: Base image with Java 17 JRE
FROM eclipse-temurin:17-jre-alpine

# Set metadata labels
LABEL maintainer="DevOps Team"
LABEL description="DevOps Demo Application Container"

# Set working directory inside the container
WORKDIR /app

# Copy the packaged Spring Boot JAR from Gradle build output
COPY build/libs/app.jar app.jar

# Expose port 8080 for application traffic
EXPOSE 8080

# Execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]
