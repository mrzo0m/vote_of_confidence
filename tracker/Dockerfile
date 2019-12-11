FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/mrzo0m/vote_of_confidence.git

FROM maven:3.6.3-jdk-8 AS maven
COPY --from=clone /app/vote_of_confidence/ /tmp/
WORKDIR /tmp/
RUN mvn -T 1C clean package -DskipTests=true -q -pl tracker -am
RUN find /tmp/tracker/target/ -name '*jar' -exec bash -c 'mv $0 app.jar' {} \;
RUN chmod +x /tmp/tracker/scripts/entrypoint.sh
VOLUME /tmp
ENTRYPOINT ["/tmp/tracker/scripts/entrypoint.sh"]
EXPOSE 8000
