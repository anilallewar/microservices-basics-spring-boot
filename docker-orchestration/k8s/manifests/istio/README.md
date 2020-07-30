#Overview

How to run the system services using Istio and helm charts

## Pre-requisites

* The docker image should have been build previously using the ../../../../docker-image-all-projects.sh
* Helm installed on the system
* Install Istio using the following commands
** Download Istio => `curl -L https://istio.io/downloadIstio | sh -`
** Make the Istio commands avaialble on path => `export PATH="$PATH:<<Istio installation directory>>/bin"`
** Set Istio default profile for development work => `istioctl install --set profile=demo`
** Add a namespace label to instruct Istio to automatically inject Envoy sidecar proxies into the "default" kubernetes namespace when you deploy your application later => `kubectl label namespace default istio-injection=enabled`
** Set the kubernetes `default` namespace to do the readiness/liveness probe using mutual TLS
*** Specify authentication policy for `default` namespace
`
kubectl apply -f - <<EOF
apiVersion: "security.istio.io/v1beta1"
kind: "PeerAuthentication"
metadata:
  name: "default"
  namespace: "default"
spec:
  mtls:
    mode: STRICT
EOF
`
*** Specify destination rule for `default` namespace
`
kubectl apply -f - <<EOF
apiVersion: "networking.istio.io/v1alpha3"
kind: "DestinationRule"
metadata:
  name: "default"
  namespace: "default"
spec:
  host: "*.default.svc.cluster.local"
  trafficPolicy:
    tls:
      mode: ISTIO_MUTUAL
EOF
`

## Running the application
* Note that the load balancer is not defined in the service for the eurekaregistry
** helm install system-svc application/system-services/ 
*** [[config]] - Name of helm release
*** [[ config-server]] - Name of the folder holding the helm charts
** kubectl get svc,pods,deployment (to check if deployment was successful)


## Checking if app is running or not
** curl http://localhost:8888/api-gateway/default
*** This should give you the contents from api-gateway.yml

## Delete the deployment
** helm delete config