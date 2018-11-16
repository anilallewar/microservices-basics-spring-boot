#!/bin/sh

cd api-gateway; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd auth-server; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd config-server; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd comments-webservice; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build publishToMavenLocal buildDocker; cd ..
cd task-webservice; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd user-webservice; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd web-portal; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd webservice-registry; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
cd zipkin-server; echo "\033[1;96m Execution directory: `pwd | xargs basename` \033[0m"; ./gradlew clean build buildDocker; cd ..
