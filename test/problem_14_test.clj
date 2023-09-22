(ns problem-14-test
  (:require [clojure.test :refer [deftest testing is]]
            [problem-14 :as p]))

(def answer 837799)

(deftest test-14
  (testing "Test task 14"
    (is (= answer (p/solve-rec)))
    (is (= answer (p/solve-tail-rec))) 
    (is (= answer (p/solve-gfr)))
    (is (= answer (p/solve-gm)))
    (is (= answer (p/solve-cycle)))
    (is (= answer (p/solve-lazy)))))