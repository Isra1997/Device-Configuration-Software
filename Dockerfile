FROM amazoncorretto:17-alpine3.18-jdk
ADD target/ConfigurationSoftware-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ConfigurationSoftware-0.0.1-SNAPSHOT.jar"]