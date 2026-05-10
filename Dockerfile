FROM gradle:jdk25-corretto AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:25.0.3_9-jdk-ubi10-minimal
COPY --from=build /home/gradle/src/build/libs/tableplanner-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
