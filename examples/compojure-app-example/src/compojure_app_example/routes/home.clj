(ns compojure-app-example.routes.home
  (:require [compojure.core :refer :all]
            [compojure-app-example.views.layout :as layout]))

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defroutes home-routes
  (GET "/" [] (home)))
