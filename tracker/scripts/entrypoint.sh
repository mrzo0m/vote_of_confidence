#!/bin/bash

cd /tmp/flyway
mvn -T 1C flyway:migrate
cd ..
java -Djava.security.egd=file:/dev/./urandom -jar /tmp/app.jar