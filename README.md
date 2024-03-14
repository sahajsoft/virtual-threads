# Virtual Threads Workshop
This repository holds the code for virtual thread workshop held in BLR office.

It contains two services
1. User Service - Service handling user related information
2. Subscription Service - Service handling subscription plans and data left for that plan.

## Pre-requisites
* Java 21
* Gradle
* Docker

## Building the application
```./gradlew clean build```

## Running the application
### Initial run
1. Create .env file with as provided in the .env.example
2. ```./gradlew clean build```
3. ```docker-compose --env-file ./.env up --build```

### Subsequent runs
1. ```./gradlew clean build```
2. ```docker-compose --env-file ./.env up --build```