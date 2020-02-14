(ns re-frame-example.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [re-frame-example.events :as events]
   [re-frame-example.views :as views]
   [re-frame-example.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
