# lando-clojure

Docker build and example app for running Clojure inside of Lando

## Prerequisites

Just Docker and [Lando](https://lando.dev).

## Running

You can run this repo as a standalone app with Lando in the standard way:

```sh
lando start
```

To start a REPL server in Lando:

```sh
lando repl
```

### Using vanilla Docker

It might be useful to know what the Docker containers are doing in order to design your use-case 

To start a web server for the application using vanilla Docker, run:

```sh
docker run -it -p 3000:3000 -v $(pwd):/app acobster/lando-clojure:lein lein ring server-headless
```

This is essentially what happens on `lando start`.

To start a REPL server:

```sh
docker run -it -p 9001:9001 acobster/lando-clojure:lein lein repl
```

To start a shadow-cljs REPL:

```sh
docker run -it -p 3000:3000 -v $(pwd):/app acobster/lando-clojure:shadow-cljs shadow-cljs cljs-repl dev
```

## Examples

A Landofile for a simple Clojure web app looks something like this:

```yaml
name: clojure-test

services:
  appserver:
    type: compose

    services:
      image: acobster/lando-clojure:lein
      command: lein ring server-headless

tooling:
  lein:
    service: appserver
    cmd: lein

proxy:
  appserver:
    - clojuretest.lndo.site:3000
```

See the [examples folder](https://github.com/acobster/lando-clojure/tree/master/examples) for full working examples. Please note these are works-in-progress.

## License

Copyright © 2020 Coby Tamayo
