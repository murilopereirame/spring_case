FROM gradle:jdk17 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build && ls /home/gradle/source/build

FROM openjdk:17-slim
COPY --from=gradleimage /home/gradle/source/build/libs/spring_case.jar /app/
WORKDIR /app
EXPOSE 8443
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring_case.jar"]