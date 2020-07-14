#! /usr/bin/env bash

kubectl exec $(kubectl get pods --namespace test | awk '/example-model/ {print $1}') -- bash -c "cd /usr/share/example-model && bash ./verify.sh"
