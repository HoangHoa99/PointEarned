FROM openjdk:11

EXPOSE 8080

COPY build/libs/*.jar userpurchased.jar

ENTRYPOINT ["java", "-jar", "userpurchased.jar"]