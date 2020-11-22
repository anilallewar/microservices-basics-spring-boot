#!/bin/sh

cd api-gateway; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build docker; cd ..
cd auth-server; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build docker; cd ..
cd config-server; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build docker; cd ..
#cd comments-webservice; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build publishToMavenLocal docker; cd ..
#cd task-webservice; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build docker; cd ..
cd user-webservice; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build docker; cd ..
cd web-portal; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build docker; cd ..
cd webservice-registry; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build docker; cd ..
# Zipkin UI support is deprecated from Spring Boot