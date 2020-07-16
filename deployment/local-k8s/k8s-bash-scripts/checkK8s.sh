#!/usr/bin/env bash

helm list
kubectl get pods -n "$1"
kubectl get pv -n "$1"
kubectl get pvc -n "$1"
kubectl get jobs -n "$1"


helm uninstall palisade -n "$1"
kubectl delete pods -n "$1" --all
kubectl delete jobs -n "$1" --all
kubectl delete pv $(kubectl get pv "$1" | awk '/palisade/ {print $1}')
kubectl delete namespaces "$1"


kubectl get pods -n "$1"
kubectl get pv -n "$1"
kubectl get pvc -n "$1"
kubectl get jobs -n "$1"