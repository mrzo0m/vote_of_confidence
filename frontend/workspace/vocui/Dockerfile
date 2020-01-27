FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM alpine/git as clone
WORKDIR /app
RUN git clone -b master https://github.com/mrzo0m/vote_of_confidence.git

FROM maven:3.5.2-jdk-8-alpine AS maven
WORKDIR /tmp/
COPY --from=clone /app/vote_of_confidence/ /tmp/
COPY --from=build-stage /app/dist /tmp/invoke_static/
RUN rm -rf /tmp/frontend/src/main/resources/static/
RUN mkdir /tmp/frontend/src/main/resources/static
RUN cp -rf /tmp/invoke_static/* /tmp/frontend/src/main/resources/static/
RUN mvn -T 1C clean package -DskipTests=true -q -pl frontend -am
RUN find /tmp/frontend/target/ -name '*jar' -exec bash -c 'mv $0 app.jar' {} \;
ENTRYPOINT /tmp

FROM openjdk:11-slim
VOLUME /tmp
COPY --from=maven /tmp/app.jar app.jar
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions", "-XX:+UseJVMCICompiler","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8080