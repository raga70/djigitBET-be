FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8080
COPY build/libs/djigitbet-0.0.1-SNAPSHOT.jar djigitbet.jar
ENTRYPOINT ["java", "-jar", "djigitbet.jar"]
