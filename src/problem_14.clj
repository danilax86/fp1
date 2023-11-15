(ns problem-14
  (:gen-class))

;; 1. Recursion
;; This one returns len of colatz seq
(defn length-rec
  [n]
  (cond 
    (= n 1) 1
    (even? n) (inc (length-rec (quot n 2))) 
    :else (inc (length-rec (inc (* 3 n))))))


;; This one returns colatz seq (recur is here)
(defn colatz-seq [starting-number]
  (loop [n starting-number 
         xs (conj [] starting-number)]
    (cond 
      (= 1 n) xs
      (even? n) (recur (quot n 2) (conj xs (quot n 2))) 
      :else (recur (inc (* n 3)) (conj xs (inc (* n 3)))))))

(defn solve-rec
  ([] (solve-rec 1000000))
  ([n]
   (letfn [(tuple [n] [n (length-rec n)])
           (maxf [a b]
             (if (> (second a)
                    (second b)) a b))]
     (first (reduce maxf (map tuple (range 1 n)))))))

(defn solve-rec-recur
  ([] (solve-rec-recur 1000000))
  ([n]
   (letfn [(tuple [n] [n (count (colatz-seq n))])
           (maxf [a b]
             (if (> (second a)
                    (second b)) a b))]
     (first (reduce maxf (map tuple (range 1 n)))))))


;; 2. Gen, filter, reduce
(defn solve-gfr
   ([] (solve-gfr 1000000))
   ([n]
    (first (reduce (fn [x y]
              (if (> (second x) (second y)) x y))
            (for [n (range 1 (inc n))]
              [n (length-rec n)])))))


;; 3. Generation with map
(defn solve-gm
  ([] (solve-gm 1000000))
   ([n]
    (first (reduce (fn [x y]
                     (if (> (second x) (second y)) x y))
                   (for [n (map inc (take n (range)))]
                     [n (length-rec n)])))))


;; 5. Lazy collections
(defn solve-lazy
  ([] (solve-lazy 1000000))
  ([n]
   (apply max-key length-rec (range 1 n))))
