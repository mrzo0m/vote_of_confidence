FROM eu.gcr.io/vote-of-confidence-233210/voc-frontend-source:latest as source
WORKDIR /app

FROM alpine/git as clone
WORKDIR /app
RUN head -c 5 /dev/random > random_bytes && git clone -b master https://github.com/mrzo0m/vote_of_confidence.git

FROM poum/senchacmd as senchacmd
WORKDIR /code
COPY --from=clone /app/vote_of_confidence/frontend/workspace/ /code
COPY --from=source /app/src /code
RUN /opt/Sencha/sencha app build production

FROM maven:3.5.2-jdk-8-alpine AS maven
COPY --from=clone /app/vote_of_confidence/ /tmp/
COPY --from=senchacmd /code/build/production/VocApp/ /tmp/frontend/src/main/resources/static/
WORKDIR /tmp/
RUN mvn -T 1C clean package -DskipTests=true -q -pl frontend -am
RUN find /tmp/frontend/target/ -name '*jar' -exec bash -c 'mv $0 app.jar' {} \;
ENTRYPOINT /tmp

FROM openjdk:11-slim
VOLUME /tmp
COPY --from=maven /tmp/app.jar app.jar
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions", "-XX:+UseJVMCICompiler","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8080