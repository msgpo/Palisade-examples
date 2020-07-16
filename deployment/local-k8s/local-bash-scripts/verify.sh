#! /usr/bin/env bash

kubectl exec $(kubectl get pods --namespace "$1" | awk '/example-model/ {print $1}') --namespace "$1" -- bash -c "cd /usr/share/example-model && bash ./verify.sh"
