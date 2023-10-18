(ns problem-17-test
  (:require [clojure.test :refer [deftest testing is]]
            [problem-17 :as p]))

(def answer 21124)

(deftest test-17
  (testing "General"
    (is (= answer (p/solve)))))