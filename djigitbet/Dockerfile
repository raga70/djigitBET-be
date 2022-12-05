FROM gradle:7.4.2-jdk17
EXPOSE 8080
ADD build/libs/djigitbet-0.0.1-SNAPSHOT.jar djigitbet.jar
ENTRYPOINT ["java", "-jar", "djigitbet.jar"]