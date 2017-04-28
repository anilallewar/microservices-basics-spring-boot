#-----------------------------------------------------------------------------------------------------
# API Gateway
#-----------------------------------------------------------------------------------------------------
FROM openjdk:8u121-jdk-alpine

MAINTAINER Anil Allewar <anilallewar@yahoo.co.in>

# Keep consistent with build.gradle 
ENV APP_JAR_NAME basic-api-gateway
ENV APP_JAR_VERSION 0.0.1

RUN mkdir /app

ADD ${APP_JAR_NAME}-${APP_JAR_VERSION}.jar /app/ 

WORKDIR /app

EXPOSE 8082

ENTRYPOINT java -Djava.security.egd=file:/dev/urandom -Dspring.profiles.active=docker -jar ${APP_JAR_NAME}-${APP_JAR_VERSION}.jar
