FROM alpine/java:21-jdk as BUILD

WORKDIR /app
COPY . .

RUN chmod +x gradlew

RUN ./gradlew build -x test

ENTRYPOINT ["java", "-jar", "./build/libs/pdf-reader-0.0.1-SNAPSHOT.jar"]