#Overview

How to run the config-service in standalone mode

## Pre-requisites

* The docker image should have been build previously using the ../../../../docker-image-all-projects.sh

## Running the application
* Once you have the Kubernetes server installed (either through Docker for Desktop or MiniKube), you can run the following commands in this folder
** kubectl create -f config-server-deployment.yml
** kubectl get deployment (to check if deployment was successful)
** kubectl get pods
** kubectl logs <name of pod>
** kubectl create -f config-server-deployment.yml
** kubectl get svc

## Checking if app is running or not
** curl http://localhost:8888/api-gateway/default
*** This should give you the contents from api-gateway.yml

## Delete the deployment
** kubectl delete -f config-server-service.yml
** kubectl delete -f config-server-deployment.yml

## Creating the deployment and service through single manifest file
** kubectl create -f config-server-single.yml
** kubectl get svc,pods,deployment
** curl http://localhost:8888/api-gateway/default (check if app is running)
** kubectl delete -f config-server-single.yml
** You can also delete resources by name
*** kubectl delete deployment/config-server service/config-server-service