FROM amazoncorretto:11-alphine-jdk
COPY stat-client/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]