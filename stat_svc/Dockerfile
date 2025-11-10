
FROM eclipse-temurin:21-jdk-jammy

COPY /Server/target/*.jar stat_svc.jar

ENTRYPOINT ["java","-jar","/main_svc.jar"]