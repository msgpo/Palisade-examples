#! /usr/bin/env bash

kubectl exec $(kubectl get pods --namespace test | awk '/example-model/ {print $1}') --namespace test -- bash -c "cd /usr/share/example-model && bash ./runK8sExample.sh"
