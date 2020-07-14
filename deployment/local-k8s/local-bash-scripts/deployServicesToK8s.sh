#! /usr/bin/env bash

helm dep up
helm upgrade --install palisade . \
  --set global.persistence.classpathJars.local.hostPath=$(pwd)/deployment/target \
  --set global.persistence.dataStores.palisade-data-store.local.hostPath=$(pwd)/resources/data \
  --set global.persistence.kafka.local.hostPath=$(pwd) \
  --set global.persistence.redisMaster.local.hostPath=$(pwd) \
  --set global.persistence.redisSlave.local.hostPath=$(pwd) \
  --set global.persistence.zookeeper.local.hostPath=$(pwd) \
  --set traefik.install=true \
  --set kafka.install=true \
  --set redis.install=true \
  --set global.hosting=local \
  --set redis-cluster.install=false \
  --timeout=200s \
  --namespace test
