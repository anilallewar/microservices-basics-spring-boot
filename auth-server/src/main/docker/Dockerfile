#-----------------------------------------------------------------------------------------------------
# Authorization Server
#-----------------------------------------------------------------------------------------------------
FROM openjdk:8u121-jdk-alpine

MAINTAINER Anil Allewar <anilallewar@yahoo.co.in>

# Keep consistent with build.gradle 
ENV APP_JAR_NAME basic-auth-server
ENV APP_JAR_VERSION 0.0.1

RUN mkdir /app

ADD ${APP_JAR_NAME}-${APP_JAR_VERSION}.jar /app/ 

WORKDIR /app

EXPOSE 8888

ENTRYPOINT java -Djava.security.egd=file:/dev/urandom -jar ${APP_JAR_NAME}-${APP_JAR_VERSION}.jar
