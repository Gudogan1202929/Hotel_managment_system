FROM openjdk:21-ea-1-slim
COPY /build/libs/*.jar /app.jar
EXPOSE 9096
CMD ["java", "-jar", "/app.jar"]