
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY /Server/target/*.jar stat_svc.jar

EXPOSE 9090

CMD ["java", "-jar", "app.jar"]