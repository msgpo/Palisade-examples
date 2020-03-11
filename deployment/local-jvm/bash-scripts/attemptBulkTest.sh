#!/usr/bin/env bash

# Run the bulk resource test
java -jar example-model/target/example-model-*-exec.jar --example.directory="$(pwd)/resources/data/employee_file0.avro" --example.type=bulk || echo "Error running example-model-<version>-exec.jar - have you run 'mvn install'?"
