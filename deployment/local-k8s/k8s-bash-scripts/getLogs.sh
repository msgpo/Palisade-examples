#!/usr/bin/env bash

sleep 60s
kubectl describe pods --namespace=$1 $(kubectl get pods --namespace $1 | awk '/'$2'/ {print $1}')