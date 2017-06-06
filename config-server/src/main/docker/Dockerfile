#-----------------------------------------------------------------------------------------------------
# Config Server
#-----------------------------------------------------------------------------------------------------
FROM openjdk:8u121-jdk-alpine

MAINTAINER Anil Allewar <anilallewar@yahoo.co.in>

# Keep consistent with build.gradle 
ENV APP_JAR_NAME basic-config-server
ENV APP_JAR_VERSION 0.0.1

# Install curl, bash and mysql-client to check if DB is up
RUN apk --update add curl bash mysql-client && \
	rm -rf /var/cache/apk/*
	
RUN mkdir /app

ADD ${APP_JAR_NAME}-${APP_JAR_VERSION}.jar /app/ 
ADD run.sh /app/
RUN chmod +x /app/run.sh 

WORKDIR /app

EXPOSE 8888

ENTRYPOINT ["/bin/bash","-c"]
CMD ["/app/run.sh"]
