FROM openjdk:17-jdk-alpine

# Create a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app

# Copy only the built JAR file
COPY target/waste_management_system-0.0.1-SNAPSHOT.jar app.jar

# Ensure proper permissions
RUN chown spring:spring app.jar && chmod 755 app.jar

USER spring:spring

# Expose the port
EXPOSE 8080

# Ensure Java uses the correct port
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
