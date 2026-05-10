FROM gradle:jdk25-openjdk AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:jdk25-openjdk
COPY --from=build /home/gradle/src/build/libs/TablePlanner-Backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
