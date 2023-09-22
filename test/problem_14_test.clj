(ns problem-14-test
  (:require [clojure.test :refer [deftest testing is]]
            [problem-14 :refer [rec-solve million]]))

(def input million)
(def answer 837799)

(deftest test-14
  (testing "Test task 14"
    (is (= (rec-solve input) answer))))