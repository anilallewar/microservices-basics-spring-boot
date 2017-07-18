#!/bin/sh

cd api-gateway; ./gradlew clean build buildDocker; cd ..
cd auth-server; ./gradlew clean build buildDocker; cd ..
cd config-server; ./gradlew clean build buildDocker; cd ..
cd comments-webservice; ./gradlew clean build publishToMavenLocal buildDocker; cd ..
cd task-webservice; ./gradlew clean build buildDocker; cd ..
cd user-webservice; ./gradlew clean build buildDocker; cd ..
cd web-portal; ./gradlew clean build buildDocker; cd ..
cd webservice-registry; ./gradlew clean build buildDocker; cd ..
cd zipkin-server; ./gradlew clean build buildDocker; cd ..
