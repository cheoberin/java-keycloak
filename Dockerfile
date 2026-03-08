FROM maven:3.9.11 AS build
WORKDIR /app
COPY pom.xml .

COPY src /app/src
RUN mvn clean install -U

FROM amazoncorretto:21-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]