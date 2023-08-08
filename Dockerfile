FROM gradle:jdk17 as gradleimage
COPY /et[c]/secret[s]/.en[v] /home/gradle/source/
COPY /et[c]/secret[s]/ec512_keypair.pe[m] /home/gradle/source/
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build && ls /home/gradle/source/build

FROM openjdk:17-slim
COPY --from=gradleimage /home/gradle/source/build/libs/spring_case.jar /app/
COPY --from=gradleimage /home/gradle/source/.en[v] /app/
COPY --from=gradleimage /home/gradle/source/ec512_keypair.pe[m] /app/
WORKDIR /app
EXPOSE 8443
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring_case.jar"]