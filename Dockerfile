FROM openjdk:11.0.11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
