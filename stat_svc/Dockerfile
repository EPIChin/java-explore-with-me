
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/stat_svc-*.jar app.jar

EXPOSE 9090

CMD ["java", "-jar", "app.jar"]