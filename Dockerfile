FROM openjdk:17-jdk-slim-buster
# Use the official OpenJDK 17 image as the base image

# Set the working directory to /app
WORKDIR /app/arena-management

# Copy the necessary files
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle gradle

# Copy the project source code
COPY src src

RUN chmod +x ./gradlew

CMD ./gradlew clean bootRun