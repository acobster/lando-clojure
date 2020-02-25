(ns luminus-example.routes.home
  (:require
   [luminus-example.layout :as layout]
   [clojure.java.io :as io]
   [luminus-example.middleware :as middleware]
   [luminus-example.posts :as posts]
   [ring.util.response]
   [datomic.client.api :as d]
   [luminus-example.db :refer [conn]]
   [ring.util.http-response :as response]))

(defn request->db [{:keys [params]}]
  (let [db (d/db conn)
        t (:t params)]
    (if t
      (d/as-of db (Integer/parseInt t))
      db)))

(defn home-page [request]
  (layout/render request "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page [request]
  (layout/render request "about.html"))

(defn post-page [{:keys [path-params] :as request}]
  (let [db (request->db request)
        post (posts/slug->post db (:slug path-params))]
    (layout/render request "post.html" {:post post
                                        :debug {:params (update (:params request) :t #(Integer/parseInt %))
                                                :db db}})))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/page/:slug" {:get post-page}]
   ["/about" {:get about-page}]])

