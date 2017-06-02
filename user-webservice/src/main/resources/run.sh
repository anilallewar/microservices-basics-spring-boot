#!/bin/bash

# The environment variables are already set up by the Dockerfile
java -Djava.security.egd=file:/dev/urandom -Dspring.profiles.active=docker -jar ${APP_JAR_NAME}-${APP_JAR_VERSION}.jar