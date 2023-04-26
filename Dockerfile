# Start with a base image containing Java runtime and MySQL server
FROM openjdk:11
RUN apt-get update && \
    apt-get install -y mysql-server

# Copy the database schema and data to the container
COPY dairyfarm.sql /docker-entrypoint-initdb.d/dairyfarm.sql

# Create a new database user and grant permissions
ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_USER=dairyfarm
ENV MYSQL_PASSWORD=dairyfarmpassword
ENV MYSQL_DATABASE=dairyfarm
RUN /etc/init.d/mysql start && \
    mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "CREATE USER '${MYSQL_USER}'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';" && \
    mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO '${MYSQL_USER}'@'%';"

# Copy the compiled JAR file from the build stage
COPY dairyfarm.jar /app/dairyfarm.jar

# Expose the default port for the JavaFX application
EXPOSE 8080

# Start the MySQL server and run the JavaFX application when the container starts
CMD /etc/init.d/mysql start && java -jar /app/dairyfarm.jar
