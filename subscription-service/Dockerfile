FROM eclipse-temurin:21.0.2_13-jre-alpine

COPY build/libs/*.jar app.jar
EXPOSE 8081
ENV JDBC_URL=db:5432
ENV JDBC_USER=postgres
ENV JDBC_PASSWORD=postgres
ENV DB_NAME=subscriptions

ENTRYPOINT ["java","-jar","/app.jar"]

