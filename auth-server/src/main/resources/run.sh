#!/bin/bash

SPRING_ACTIVE_PROFILE=${SPRING_ACTIVE_PROFILE:-"local-docker"}
GIT_BRANCH_LABEL=${GIT_BRANCH_LABEL:-"develop"}

# The environment variables are already set up by the Dockerfile
exec java -Djava.security.egd=file:/dev/urandom -Dspring.profiles.active=${SPRING_ACTIVE_PROFILE} -Dgit.config.active.branch=${GIT_BRANCH_LABEL} -Duser.timezone=Asia/Kolkata -XX:+PrintFlagsFinal $JAVA_OPTIONS -jar ${APP_JAR_NAME}-${APP_JAR_VERSION}.jar