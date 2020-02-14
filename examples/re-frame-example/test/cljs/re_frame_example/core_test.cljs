(ns re-frame-example.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-example.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
