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

## License

Copyright © 2020 Coby Tamayo
