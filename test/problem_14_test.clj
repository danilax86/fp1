(ns problem-14-test
  (:require [clojure.test :refer [deftest testing is]]
            [problem-14 :as p]))

(def answer 837799)

(deftest test-14
  (testing "Recursion"
    (is (= answer (p/solve-rec))))
  (testing "Generation"
    (is (= answer (p/solve-gfr)))
    (is (= answer (p/solve-gm))))
  (testing "Special"
    (is (= answer (p/solve-lazy)))))