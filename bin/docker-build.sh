#!/bin/bash

usage() {
  echo
  echo 'Build the acobster/lando-clojure:<TAG> image in the current directory'
  echo
  echo '  docker-build.sh <TAG>'
  echo
}

POSITIONAL=()
while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -h|--help)
    # show usage and bail
    usage
    exit
    ;;
    -l|--latest)
    LATEST=1
    shift # past argument
    ;;
    -t|--tag)
    TAG="$2"
    shift # past argument
    shift # past value
    ;;
    *)
    POSITIONAL+=("$1") # save it in an array for later
    shift # past argument
    ;;
esac
done
set -- "${POSITIONAL[@]}" # restore positional parameters


TAG=${TAG:-'test'}

echo "building base acobster/lando-clojure:cli-tools image..."
docker build --tag acobster/lando-clojure:cli-tools --file docker/Dockerfile.cli-tools .

docker build --tag acobster/lando-clojure:"$TAG" --file docker/Dockerfile.lein .
