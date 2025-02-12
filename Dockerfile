## Use an official JDK image to build the app
#FROM eclipse-temurin:17-jdk-jammy AS build
#
#WORKDIR /app
#
## Copy Maven wrapper and dependency files
#COPY mvnw ./
#COPY .mvn .mvn
#COPY pom.xml ./
#
## Download dependencies to leverage Docker caching
#RUN ./mvnw dependency:go-offline -DskipTests
#
## Copy source code and build application
#COPY src ./src
#RUN ./mvnw package -DskipTests
#
## Use a minimal JRE image to run the app
#FROM eclipse-temurin:21-jre-jammy AS runtime
#
#WORKDIR /app
#
## Copy built JAR from the build stage
#COPY --from=build /app/target/*.jar app.jar
#
## Run the application
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

#FROM eclipse-temurin:17-jdk-focal
#
#WORKDIR /app
#
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#
#COPY src ./src
#
#CMD ["java", "-jar", "target/waste_management_system-0.0.1-SNAPSHOT.jar"]
#
#EXPOSE 8080

#FROM openjdk:17
#WORKDIR /app
#COPY target/waste_management_system-0.0.1-SNAPSHOT.jar .
#EXPOSE 8080
#CMD ["java", "-jar", "waste_management_system-0.0.1-SNAPSHOT.jar"]


# FROM eclipse-temurin:17-jdk-focal
 
# WORKDIR /app
 
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline
 
# COPY src ./src
 
# CMD ["./mvnw", "spring-boot:run"]

FROM openjdk:17
ADD target/waste_management_system-0.0.1-SNAPSHOT.jar app1.jar
ENTRYPOINT [ "java", "-jar","app1.jar" ]
