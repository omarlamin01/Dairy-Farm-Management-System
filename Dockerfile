## Start with a fresh image that only contains the JRE
#FROM openjdk:8-jre-alpine
#
## Set the working directory
#WORKDIR /app
#
## Copy the compiled JAR file from the build stage
#COPY /out/artifacts/Dairy_Farm_Management_System_jar/Dairy_Farm_Management_System.jar /app/grass-land-dairy.jar
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

FROM openjdk:11
FROM ubuntu:latest
FROM mysql:latest

# copy the required files from the local machine to the container
COPY . /app

# set the working directory to the directory where the files were copied
WORKDIR /app

COPY /out/artifacts/Dairy_Farm_Management_System_jar/Dairy_Farm_Management_System.jar /app/grass-land-dairy.jar

# install MySQL
RUN apt-get update && apt-get install -y mysql-server

ENV MYSQL_HOST=localhost
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_DATABASE=dairyfarm

#start the MySQL server
RUN service mysql start

# create the database
RUN mysql -u root -e "CREATE DATABASE dairyfarm"

# copy the SQL file to the container
COPY dairyfarm.sql /docker-entrypoint-initdb.d

# execute the SQL file
RUN mysql -u root dairyfarm < dairyfarm.sql
#
## execute the SQL script
#RUN mysql -u root -p < dairyfarm.sql

# start the JavaFX application
CMD ["java", "-jar", "grass-land-dairy.jar"]
