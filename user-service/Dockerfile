FROM eclipse-temurin:21.0.2_13-jre-alpine

COPY build/libs/*.jar app.jar
EXPOSE 8082
ENV POSTGRES_CONNECT_URL=jdbc:postgresql://db:5432/users
ENV POSTGRES_USERNAME=postgres
ENV POSTGRES_PASSWORD=postgres
ENV SUBSCRIPTION_SERVICE_HOST=localhost
ENV SUBSCRIPTION_SERVICE_PORT=8081

ENTRYPOINT ["java","-jar","/app.jar"]

