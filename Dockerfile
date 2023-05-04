FROM openjdk:17-jdk-slim-buster
# Use the official OpenJDK 17 image as the base image

# Set the working directory to /app
WORKDIR /app

# Copy the application JAR file to the container
COPY build/libs/reserveService.jar /app

# Set the command to run the application when the container starts
CMD ["java", "-jar", "reserveService.jar"]
