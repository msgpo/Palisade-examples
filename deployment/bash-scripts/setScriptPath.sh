#!/bin/bash
# sets up the different paths for calling deployment scripts

export EXAMPLE=$(pwd)
export K8SBASHSCRIPTS="$EXAMPLE/local-k8s/bash-scripts"
export LOCALJVMBASHSCRIPTS="$EXAMPLE/local-jvm/bash-scripts"
export GENERICSCRIPTS="$EXAMPLE/bash-scripts"