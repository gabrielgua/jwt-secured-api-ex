FROM openjdk:17

WORKDIR /app

COPY target/Jwt-example-0.0.1-SNAPSHOT.jar /app/api.jar
EXPOSE 8080

CMD ["java", "-jar", "api.jar"]