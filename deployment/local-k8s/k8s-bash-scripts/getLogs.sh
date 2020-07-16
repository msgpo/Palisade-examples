#!/usr/bin/env bash

kubectl logs $(kubectl get pods $1 | awk '/'$2'/ {print $1}')