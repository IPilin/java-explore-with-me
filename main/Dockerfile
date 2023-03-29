FROM amazoncorretto:11-alpine-jdk
COPY main-app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]