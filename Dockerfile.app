FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/lenny-0.0.1-SNAPSHOT.jar app.jar
# COPY lenny-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
