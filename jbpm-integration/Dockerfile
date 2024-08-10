# Start with a base image containing Java runtime
FROM openjdk:17

# The application's jar file
ARG JAR_FILE=build/libs/jbpm-integration-0.0.1-SNAPSHOT.jar

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 5002

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
