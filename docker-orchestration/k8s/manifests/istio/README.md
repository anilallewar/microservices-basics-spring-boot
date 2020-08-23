#Overview

How to run the system services using Istio and helm charts

## Pre-requisites

* The docker image should have been build previously using the ../../../../docker-image-all-projects.sh
* Helm installed on the system
* Install Istio using the following commands
** Download Istio => `curl -L https://istio.io/downloadIstio | sh -`
** Make the Istio commands avaialble on path => `export PATH="$PATH:<<Istio installation directory>>/bin"`
** Set Istio default profile for development work => `istioctl install --set profile=demo`
** Delete existing namespace if it exists => `kubectl delete namespace istio-io-health`
** Create a brand new namespace => `kubectl create namespace istio-io-health`
** Add a namespace label to instruct Istio to automatically inject Envoy sidecar proxies into the "default" kubernetes namespace when you deploy your application later => `kubectl label namespace istio-io-health istio-injection=enabled`
** Check whether the current context is set to the `istio-io-health` namespace => `kubectl config get-context`
** If the current context namespace us not set to `istio-io-health` then exceute command to set the current namespace => `kubectl config set-context --current --namespace=istio-io-health`

** Set the kubernetes `istio-io-health` namespace to do the readiness/liveness probe using mutual TLS
*** Specify authentication policy for `istio-io-health` namespace => `kubectl apply -f istio-auth-policy.yml`
*** Specify destination rule for `istio-io-health` namespace => `kubectl apply -f istio-destination-rule.yml`

## Running the application
* Note that the load balancer is not defined in the service for the eurekaregistry
** `helm install system-svc application/system-services/ `
*** [[ system-svc ]] - Name of helm release
*** [[ application/system-services/ ]] - Name of the folder holding the helm charts
** kubectl get svc,pods,deployment (to check if deployment was successful)

## NOTE - The application is currently failing since it's not able to negotiate the TLS handshake as part of Istio mutual TLS authentication.