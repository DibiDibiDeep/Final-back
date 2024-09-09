FROM openjdk:17-jdk
COPY build/libs/finalProj-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
