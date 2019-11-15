(ns lando-clojure.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.string :as string]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "WAT YR NAME")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
