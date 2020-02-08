#!/usr/bin/env bash


# STYLES

export GRN='\033[32m'
export RED='\033[31m'
export BLD='\033[1m'
export RST='\033[0m'


usage() {
    echo 'Usage: ./lando_test.sh [-d|--destroy]'
    echo
    echo 'OPTIONS:'
    echo
    echo '  -d|--destroy: destroy the current Lando app, if any'
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
    -d|--destroy)
    DESTROY=1
    shift # past argument
    ;;
    *)
    POSITIONAL+=("$1") # save it in an array for later
    shift # past argument
    ;;
esac
done
set -- "${POSITIONAL[@]}" # restore positional parameters

main() {
    if [[ $DESTROY ]]
    then
        lando destroy -y
    fi


    lando start -vvv

    if [[ ! $? = '0' ]]
    then
        err '`lando start` failed!'
        exit 1
    fi

    lando test

    if [[ ! $? = '0' ]]
    then
        err '`lando test` failed!'
        exit 1
    fi

    echo -e "${GRN}OK${RST}"
}

err() {
    echo -e "${BLD}${RED}${1}${RST}"
}


main