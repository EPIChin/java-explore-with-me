
FROM eclipse-temurin:21-jdk-jammy

LABEL org.opencontainers.image.description="Explore With Me Stats Service" \
      org.opencontainers.image.version="0.0.1-SNAPSHOT"

WORKDIR /app

COPY target/stat_svc-*.jar app.jar

EXPOSE 9090

CMD ["java", "-jar", "app.jar"]