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

# Extract version from pom.xml
RUN VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout) && \
    echo "VERSION=$VERSION" && \
    mv target/pop-analysis-${VERSION}-jar-with-dependencies.jar ./pop-analysis.jar
    
# Runtime image (smaller)
FROM eclipse-temurin:21-jre

WORKDIR /app

# Extract JAR file from build
COPY --from=build /app/pop-analysis.jar pop-analysis.jar
COPY docker/rebuild_private_key.sh .
COPY docker/entrypoint.sh .

# Run the app
ENTRYPOINT ["./rebuild_private_key.sh", "./entrypoint.sh"]
