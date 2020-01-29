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

if [[ $? != 0 ]] ; then
  echo 'failed to run cli-tools'
  exit 1
fi

# Test the Leiningen Docker image
docker run -it acobster/lando-clojure:LOCAL-TEST lein

if [[ $? != 0 ]] ; then
  echo 'failed to run lein via docker'
  exit 1
fi

echo OK
