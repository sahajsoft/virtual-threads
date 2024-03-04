FROM eclipse-temurin:21.0.2_13-jdk as BUILDER

RUN adduser --shell /bin/bash --disabled-password --home /home/user-service user-service

RUN mkdir /home/user-service/app
RUN cd /home/user-service/app
RUN mkdir logs
RUN mkdir logs/archive
RUN chmod 0755 /home/user-service/app

COPY ./gradle.properties ./gradle.properties
COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./build.gradle ./build.gradle
CMD ls -al
RUN ./gradlew clean dependencies

COPY . .
RUN ./gradlew build

FROM eclipse-temurin:21.0.2_13-jdk

WORKDIR /home/user-service/app
COPY ./build/libs/user-service-1.0.jar user-service-1.0.jar

EXPOSE 8082

ENV JDBC_URL=db:5432
ENV JDBC_USER=postgres
ENV JDBC_PASSWORD=postgres
ENV DB_NAME=users

CMD java $JAVA_OPTS -jar -DAPP_VERSION=1.0 user-service-1.0.jar
