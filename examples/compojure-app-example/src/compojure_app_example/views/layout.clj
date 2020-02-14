(ns compojure-app-example.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to compojure-app-example"]
     (include-css "/css/screen.css")]
    [:body body]))
