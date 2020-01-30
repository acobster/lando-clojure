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
lando lein repl
```

### Using Docker

To start a web server for the application using vanilla Docker, run:

```sh
docker run -it -p 3000:3000 -v $(pwd):/app acobster/lando-clojure:lein lein ring server-headless
```

To start a REPL server:

```sh
docker run -it -p 9001:9001 acobster/lando-clojure:lein lein repl
```

## License

Copyright © 2020 Coby Tamayo
