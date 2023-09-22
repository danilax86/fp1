(ns test-14
  (:require [clojure.test :refer :all]
            [problem-14 :refer :all]))

(def input million)
(def answer 837799)

(deftest test-14
  (testing "Test task 14"
    (is (= (rec-solve input) answer))))