#!/usr/bin/env bash

sleep 60s
kubectl logs $(kubectl get pods $1 | awk '/'$2'/ {print $1}')