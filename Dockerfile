FROM devwithlando/util:stable

# Install Java
RUN apt-get update && \
  apt-get install --yes default-jdk default-jre curl

# Install Clojure
RUN wget -O ./install-clojure.sh https://download.clojure.org/install/linux-install-1.9.0.397.sh && \
  chmod +x ./install-clojure.sh && \
  ./install-clojure.sh && \
  rm ./install-clojure.sh

# Install Leiningen
RUN wget -O /usr/local/bin/lein https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein && \
  chmod a+x /usr/local/bin/lein && \
  lein

ENV PORT=3000
EXPOSE 3000

# Install clojure 1.9.0 so users don't have to download it every time
RUN echo '(defproject dummy "" :dependencies [[org.clojure/clojure "1.9.0"]])' > project.clj \
&& lein deps && rm project.clj

COPY ./entrypoint.sh .
RUN chmod +x ./entrypoint.sh

ENTRYPOINT ["/bin/sh", "-c", "./entrypoint.sh"]
