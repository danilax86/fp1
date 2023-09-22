(ns problem-14-test
  (:require [clojure.test :refer [deftest testing is]]
            [problem-14 :as p]))

(def answer 837799)

(deftest test-14
  (testing "Test task 14"
    (is (= (p/solve) answer))))