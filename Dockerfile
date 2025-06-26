# Use Maven to build and JDK to run
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build

# Set working directory inside the container
WORKDIR /app

# Add authentication settings for GitHub Packages
COPY settings.xml /root/.m2/settings.xml

# Copy pom.xml and download dependencies (cache layers)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the jar (with dependencies)
RUN mvn clean package assembly:single

# Runtime image (smaller)
FROM eclipse-temurin:21-jre

WORKDIR /app

# Extract JAR file from build
COPY --from=build /app/target/pop-analysis-1.0-SNAPSHOT-jar-with-dependencies.jar pop-analysis.jar
COPY docker/entrypoint.sh .

# Run the app
ENTRYPOINT ["./entrypoint.sh"]
