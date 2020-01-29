#!/bin/bash

usage() {
  echo
  echo 'Build and test the various acobster/lando-clojure:<TAG> images'
  echo
  echo '  test-docker-build.sh <TAG>'
  echo
}

dir=$(dirname "$0")

$dir/docker-build.sh -t LOCAL-TEST

# Test the cli-tools Docker image
docker run -it acobster/lando-clojure:cli-tools clojure -h

# Test the Leiningen Docker image
docker run -it acobster/lando-clojure:LOCAL-TEST lein
