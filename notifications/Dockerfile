FROM alpine/git as clone
WORKDIR /app
RUN git clone -b master https://github.com/mrzo0m/vote_of_confidence.git

FROM maven:3.5.2-jdk-8-alpine AS maven
COPY --from=clone /app/vote_of_confidence/ /tmp/
WORKDIR /tmp/
RUN mvn -T 1C clean package -DskipTests=true -q -pl notifications -am
RUN find /tmp/notifications/target/ -name '*jar' -exec bash -c 'mv $0 app.jar' {} \;
ENTRYPOINT /tmp

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=maven /tmp/app.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8000