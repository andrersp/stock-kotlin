FROM gradle:jdk17-alpine as build-image
WORKDIR /app
COPY --chown=gradle:gradle . .
# Build the application.
RUN gradle build --stacktrace

FROM eclipse-temurin:17.0.7_7-jre-alpine
COPY --from=build-image /app/build/libs/stock-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT java -jar app.jar
