FROM openjdk:21-ea-1-slim
COPY /build/libs/*.jar /hms-api.jar
EXPOSE 9096
CMD ["java", "--enable-preview" , "-jar", "/hms-api.jar"]