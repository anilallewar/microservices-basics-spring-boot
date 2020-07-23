#Overview

How to run the config-service using help charts

## Pre-requisites

* The docker image should have been build previously using the ../../../../docker-image-all-projects.sh

## Running the application
* Once you have the helm CLI installed
** helm install config config-server/ 
*** [[config]] - Name of helm release
*** [[ config-server]] - Name of the folder holding the helm charts
** kubectl get svc,pods,deployment (to check if deployment was successful)


## Checking if app is running or not
** curl http://localhost:8888/api-gateway/default
*** This should give you the contents from api-gateway.yml

## Delete the deployment
** helm delete config