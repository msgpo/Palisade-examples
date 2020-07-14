#! /usr/bin/env bash

kubectl exec $(kubectl get pods --all-namespaces | awk '/example-model/ {print $1}') -- bash -c "cd /usr/share/example-model && bash ./runFormattedK8sExample.sh"
