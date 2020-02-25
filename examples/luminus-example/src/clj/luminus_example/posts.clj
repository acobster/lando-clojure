(ns luminus-example.posts
  (:require
   [clojure.string :refer [join lower-case split]]
   [datomic.client.api :as d]
   [luminus-example.db :refer [conn]]))


(defn slug->post [db slug]
  (ffirst (d/q '[:find (pull ?p [[:post/id :as :id]
                                 [:post/slug :as :slug]
                                 [:post/title :as :title]
                                 [:post/type :as :type]
                                 [:post/content :as :content]])
                 :in $ ?slug
                 :where
                 [?p :post/slug ?slug]]
               db
               slug)))

(defn title->slug [title]
  (join "-" (split (lower-case title) #" ")))


(defn type->posts [db post-type]
  (d/q '[:find (pull ?p [[:post/id :as :id]
                         [:post/slug :as :slug]
                         [:post/title :as :title]
                         [:post/content :as :content]
                         [:post/type :as :type]])
         :in $ ?type
         :where
         [?p :post/type :page]]
       db
       post-type))

(defn pages [db]
  (type->posts db :page))


(defn update-post!
  ([id data doc]
   (let [id (if (instance? java.util.UUID id) id (java.util.UUID/fromString id))
         tx-data [(vec (apply concat [:db/add [:post/id id]] data))
                  [:db/add "datomic.tx"
                   :db/doc doc]]]
     (d/transact conn {:tx-data tx-data})))
  ([id data]
   (update-post! id data "update post")))

(defn create-post! 
  ([{:keys [type title slug content]} doc]
   (let [id (java.util.UUID/randomUUID)
         type (or type :page)
         title (or title (str "Page " id))
         slug (or slug (title->slug title))
         content (or content [])]
     (d/transact conn {:tx-data [{:post/id id
                                  :post/type type
                                  :post/slug slug
                                  :post/title title
                                  :post/content content}
                                 [:db/add "datomic.tx"
                                  :db/doc doc]]})))
  ([post]
   (create-post! post "create post")))

(comment

  ;; The Post API allows for time travel!
  (slug->post (d/db conn) "my-page")
  (slug->post (d/as-of (d/db conn) 1009) "my-page")

  (pages (d/db conn))

  (create-post! {:title "Another New Page"})

  (update-post! "b7266cb9-8276-4b14-b4f5-d66faedda934"
                {:post/title "New title"})

  (update-post! "8d6149a4-023d-41b1-9128-7ea22cbb988d"
                {:post/content "Added Content"}
                "custom revision message")

  ;;
  )