(ns problem-14
  (:gen-class))

(defn apply-formula [n]
  (if (even? n)
    (quot n 2)
    (inc (* 3 n))))

(apply-formula 40)

;; 1. Recursion and tail recursion

;; Recursion
;; FIXME stackoverflow :(
(def length-rec
  (memoize
   (fn [n]
     (cond
       (<= n 0)  0
       (= n 1)   1
       :else (inc (length-rec (apply-formula n)))))))

(defn solve-rec
  ([] (solve-rec 1e6))
  ([n]
  (letfn [(tuple [n] [n (length-rec n)])
          (maxf [a b]
            (if (> (second a)
                   (second b)) a b))]
    (first (reduce maxf (map tuple (range 1 n)))))))


;; Tail recursion

(defn solve-tail-rec
  ([] (solve-tail-rec 1e6))
  ([n]
   n)) ;; todo


;; 2. Gen, filter, reduce

(defn solve-gfr
   ([] (solve-gfr 1e6))
   ([n]
    n)) ;; todo)


;; 3. Generation with map

(defn solve-gm
  ([] (solve-gm 1e6))
  ([n]
   n)) ;; todo)


;; 4. Special syntax for cycle

(defn solve-cycle
  ([] (solve-cycle 1e6))
  ([n]
   n)) ;; todo)


;; 5. Lazy collections

(defn solve-lazy
  ([] (solve-lazy 1e6))
  ([n]
   n)) ;; todo)

;; Traditional language

