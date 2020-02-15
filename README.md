# lando-clojure

Docker build and example app for running Clojure inside of Lando

## Prerequisites

Just Docker and [Lando](https://lando.dev).

## Why?

This project has two main goals:

1. Portability: remove barrier to entry for folks already invested in Lando, but who may not have the JDK, Leiningen, etc. on ther machines.
2. Environment parity: Lando is based on [services](https://docs.lando.dev/config/services.html) running in disparate containers which can all talk to each other, just like they would in production. If your Clojure app relies on a database, that's one less service you have to set up per machine.

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

      environment:
        - DATABASE_URL=postgres://postgres:@dbhost/dbname

  dbhost:
    type: postgres:11
    creds:
      database: dbname

tooling:
  lein:
    service: appserver
    cmd: lein

proxy:
  appserver:
    - clojuretest.lndo.site:3000
```

This will start a headless Ring server when you run `lando start`, and serve it at `http://clojuretest.lndo.site`. The `tooling` part 

See the [examples folder](https://github.com/acobster/lando-clojure/tree/master/examples) for full working examples. Please note these are works-in-progress.

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

## License

Copyright © 2020 Coby Tamayo
