FROM eclipse-temurin:21.0.2_13-jre-alpine

COPY build/libs/*.jar app.jar
EXPOSE 8081
ENV POSTGRES_CONNECT_URL=jdbc:postgresql://db:5432/subscriptions
ENV POSTGRES_USERNAME=postgres
ENV POSTGRES_PASSWORD=postgres

ENTRYPOINT ["java","-jar","/app.jar"]

