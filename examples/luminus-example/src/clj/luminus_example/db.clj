(ns luminus-example.db
  (:require
   [datomic.client.api :as d]
   [mount.core :as mount]))


(mount/defstate conn
  :start
  (d/connect (d/client {:server-type :peer-server
                        :access-key "asdf"
                        :secret "qwerty"
                        :endpoint "localhost:8998"
                        :validate-hostnames false})
             {:db-name "hello"})
  :stop
  (constantly nil))


(mount/defstate ^{:on-reload :noop} transact-schema
  :start
  (let [schema [{:db/ident       :post/id
                 :db/valueType   :db.type/uuid
                 :db/cardinality :db.cardinality/one
                 :db/unique      :db.unique/identity}
                {:db/ident       :post/slug
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}
                {:db/ident       :post/type
                 :db/valueType   :db.type/keyword
                 :db/cardinality :db.cardinality/one}
                {:db/ident       :post/title
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}
                {:db/ident       :post/content
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/many}]]
    (d/transact conn {:tx-data schema}))

  :stop
  (constantly nil))

(mount/defstate ^{:on-reload :noop} transact-data
  :start
  (let [data [{:post/id      #uuid "ca88a3eb-11fa-4526-994d-f7ab587c0348"
               :post/title   "My Page"
               :post/slug    "my-page"
               :post/type    :page
               :post/content ["Content v1" "More content"]}

              {:post/id      #uuid "8d6149a4-023d-41b1-9128-7ea22cbb988d"
               :post/title   "Another Page"
               :post/slug    "another-page"
               :post/type    :page
               :post/content ["Another Content v1" "MOAR content"]}

              {:post/id      #uuid "b2e32ece-c6b6-4936-ba39-d24f717dcd4d"
               :post/title   "Third Page"
               :post/slug    "page-3"
               :post/type    :page
               :post/content ["Content v3.1" "EVN MOAR CONTENT"]}

              {:post/id      #uuid "d0f0e165-7511-4520-93ec-5b927476ee6c"
               :post/title   "Blog Article"
               :post/slug    "an-article"
               :post/type    :article
               :post/content ["BLOG CONTENT"]}

              {:post/id      #uuid "08ee226d-3721-4a8a-9b69-47025e5a3c31"
               :post/title   "Article II"
               :post/slug    "article-ii"
               :post/type    :article
               :post/content ["This article says I can do" "whatever I want"]}]]
    (d/transact conn {:tx-data data}))

  :stop
  (constantly nil))

(mount/defstate ^{:on-reload :noop} add-posts
  :start
  (let [data [{:post/id      #uuid "4b2877b0-7a48-4527-a929-9bb2e6a28d7a"
               :post/title   "Additional Page"
               :post/slug    "additional-page"
               :post/type    :page
               :post/content ["Content v1" "More content"]}

              {:post/id      #uuid "f21b833e-0887-4909-8ba0-82b3c89efb21"
               :post/title   "Article III"
               :post/slug    "article-iii"
               :post/type    :article
               :post/content ["Lorem ipsum dolor sit amet"]}]]
    (d/transact conn {:tx-data data}))

  :stop
  (constantly nil))

(mount/defstate ^{:on-reload :noop} update-posts
  :start
  (let [data [[:db/add [:post/id #uuid "f21b833e-0887-4909-8ba0-82b3c89efb21"] :post/content "Added content"]
              [:db/add [:post/id #uuid "ca88a3eb-11fa-4526-994d-f7ab587c0348"] :post/title "My Edited Title"]]]
    (d/transact conn {:tx-data data}))

  :stop
  (constantly nil))
