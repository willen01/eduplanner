FROM eclipse-temurin:21

WORKDIR /app

COPY target/eduplanner-0.0.1-SNAPSHOT.jar /app/application.jar

EXPOSE 8080

CMD ["java", "-jar", "application.jar"]