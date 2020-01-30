#!/bin/bash

echo "building base acobster/lando-clojure:cli-tools image..."
docker build --tag acobster/lando-clojure:cli-tools --file docker/Dockerfile.cli-tools .

echo "building acobster/lando-clojure:lein image..."
docker build --tag acobster/lando-clojure:lein --file docker/Dockerfile.lein .

echo "building acobster/lando-clojure:shadow-cljs image..."
docker build --tag acobster/lando-clojure:shadow-cljs --file docker/Dockerfile.shadow-cljs .
