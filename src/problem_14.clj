(ns problem-14
  (:gen-class))

;; 1. Recursion
;; This one returns len of colatz seq
(defn length-rec
  [n]
  (if (= n 1)
    1
    (if (even? n)
      (inc (length-rec (quot n 2)))
      (inc (length-rec (inc (* 3 n)))))))

;; This one returns colatz seq
(defn colatz-seq [starting-number]
  (loop [n starting-number 
         xs (conj [] starting-number)]
    (if (= 1 n)
      xs
      (if (even? n)
        (recur (quot n 2) (conj xs (quot n 2)))
        (recur (inc (* n 3)) (conj xs (inc (* n 3))))))))

(defn solve-rec
  ([] (solve-rec 1000000))
  ([n]
  (loop [temp-n n 
         final-number 1 
         final-length 1]
    (let [temp-length (count (colatz-seq temp-n)) ;; We can replace (count (colatz-seq temp-n)) with (lenght-rec temp-n)
          current-winner (if (> temp-length final-length)
                           temp-n
                           final-number)
          final-length (max final-length temp-length)]

      (if (= 1 temp-n)
        final-number
        (recur (dec temp-n)
               current-winner
               final-length))))))


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
