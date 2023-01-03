# Start with a base image containing Java runtime
FROM openjdk:11

# copy the required files from the local machine to the container
COPY . /app

# set the working directory to the directory where the files were copied
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY dairyfarm.jar /app/dairyfarm.jar


# start the JavaFX application
CMD ["java", "-jar", "dairyfarm.jar"]


## Start with a fresh image that only contains the JRE
#FROM openjdk:8-jre-alpine
#
## Expose the default port for the JavaFX application
#EXPOSE 8080
#
## Run the JavaFX application when the container starts
#CMD ["java", "-jar", "grass-land-dairy.jar"]
#
## TO RUN THE CONTAINER:
##docker run --name my-mysql-db -e MYSQL_ROOT_PASSWORD=password -d mysql:5.7
##docker run --name my-javafx-app --link my-mysql-db:mysql -p 8080:8080 my-javafx-app
#
## EXECUTE THE SQL FILE:
#COPY /src/main/java/com/dfms/dairy_farm_management_system/connection/dairyfarm.sql /app/dairyfarm.sql
#
#CMD ["mysql", "-u", "root", "-p", "password", "-h", "mysql", "<", "dairyfarm.sql"]


#CMD java -jar /app/grass-land-dairy.jar