#! /usr/bin/env bash

kubectl exec $(kubectl get pods --namespace pal-544-ad | awk '/example-model/ {print $1}') --namespace pal-544-ad -- bash -c "cd /usr/share/example-model && bash ./runFormattedK8sExample.sh"
