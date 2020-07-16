#!/usr/bin/env bash

sleep 60s
kubectl --namespace $1 logs $(kubectl get pods --namespace $1 | awk '/'$2'/ {print $1}')