FROM registry.access.redhat.com/ubi8/openjdk-17:1.16

ENV LANGUAGE='en_US:en'

# Copy the built jar
COPY target/vanahub-weather-quarkus-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose port 8080 (or whatever port you use)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-Dquarkus.http.host=0.0.0.0", "-jar", "/app/app.jar"]
