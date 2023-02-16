FROM openjdk:17-jdk-slim AS build
ARG BUILD_MODULE
WORKDIR /app
COPY . .
RUN ./gradlew ${BUILD_MODULE}:clean ${BUILD_MODULE}:build

FROM eclipse-temurin:17-jre-alpine
ARG BUILD_MODULE
ARG JAR_FILE_PATH=/app/${BUILD_MODULE}/build/libs/${BUILD_MODULE}.jar
COPY --from=build ${JAR_FILE_PATH} app.jar
CMD ["java", "-jar", "app.jar"]
