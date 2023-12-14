#Generate the build
FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

#Dockerize
FROM eclipse-temurin:17.0.8.1_1-jdk-ubi9-minimal
WORKDIR /app
COPY --from=build /app/target/BlanatJar.jar ./BlanatJar.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","BlanatJar.jar"]

