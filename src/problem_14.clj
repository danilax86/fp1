(ns problem-14
  (:gen-class))

(def million 1000000)

(defn apply-formula [n]
  (if (even? n)
    (quot n 2)
    (inc (* 3 n))))

(apply-formula 40)

;; 1. Recursion and tail recursion

;; Recursion

(def length-rec
  (memoize
   (fn [n]
     (cond
       (<= n 0)  0
       (= n 1)   1
       :else (inc (length-rec (apply-formula n)))))))

(defn rec-solve [n]
  (letfn [(tuple [n] [n (length-rec n)])
          (maxf [a b]
            (if (> (second a)
                   (second b)) a b))]
    (first (reduce maxf (map tuple (range 1 n))))))


;; Tail recursion


;; 2. Gen, filter, reduce
;; 3. Generation with map
;; 4. Special syntax for cycle
;; 5. Lazy collections


;; Traditional language

