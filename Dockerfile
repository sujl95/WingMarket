FROM openjdk:11.0.11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app/wingmarket.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-Dfile.encoding=UTF-8", "-jar", "/app/wingmarket.jar"]
