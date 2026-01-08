# Stage 1: Build the application
FROM maven:3.8.5-openjdk-18-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Package the application (skip tests in Docker build to save time/complexity, assume CI runs tests)
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:18-slim
WORKDIR /app
# Copy the Fat JAR from the build stage
COPY --from=build /app/target/Dairy_Farm_Management_System-1.0-SNAPSHOT.jar app.jar

# Environment variables (can be overridden by docker-compose)
ENV DB_USER=root
ENV DB_PASSWORD=password
ENV DB_HOST=localhost

# Command to run the application
# Note: Since this is a GUI app, it needs an X Server (Display) to actually show UI.
# This CMD will start it, but it might fail without a display. 
# For headless environments, specific flags are needed, but for "running", this is correct.
CMD ["java", "-jar", "app.jar"]