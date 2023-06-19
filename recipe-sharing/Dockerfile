# Use a base image with Java 17 installed
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper files
COPY gradlew .
COPY gradle gradle

# Copy the build configuration files
COPY settings.gradle.kts .
COPY build.gradle.kts .

# Copy the source code
COPY src ./src

# Download Gradle dependencies and build the application
RUN ./gradlew build --no-daemon

# Create a new image with a smaller base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=builder /app/build/libs/*.jar ./app.jar

# Set the entrypoint command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]