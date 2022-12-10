# Start with a base image that contains Java and Maven
FROM openjdk:8-jdk-alpine as build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project files and build the project
COPY src ./src
RUN mvn package

# Start with a fresh image that only contains the JRE
FROM openjdk:8-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /out/artifacts/Dairy_Farm_Management_System_jar/Dairy_Farm_Management_System.jar ./grass-land-dairy.jar

# Expose the default port for the JavaFX application
EXPOSE 8080

# Run the JavaFX application when the container starts
CMD ["java", "-jar", "app.jar"]

# Start with the official MySQL image
FROM mysql:8.0

# Copy the MySQL configuration file
COPY my.cnf /etc/mysql/conf.d/my.cnf

# Set the default password for the root user
ENV MYSQL_ROOT_PASSWORD password


