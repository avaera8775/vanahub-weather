FROM eclipse-temurin:17-jre-alpine
WORKDIR /work/
COPY target/quarkus-app/ ./
CMD ["java", "-jar", "quarkus-run.jar"]
