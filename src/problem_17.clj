(ns problem-17
  (:gen-class))

(def nums
  ["" "one" "two" "three" "four" "five"
   "six" "seven" "eight" "nine" "ten" 
   "eleven" "twelve" "thirteen" "fourteen" "fifteen"
   "sixteen" "seventeen" "eighteen" "nineteen"])

(def tens ["" "" "twenty" "thirty" "forty" "fifty"
           "sixty" "seventy" "eighty" "ninety"])


;; Recursion
(defn convertNumToWord [x]
  (cond (= x 1000) "one thousand"
        
        (>= x 100) (str (convertNumToWord (int (quot x 100)))
                        " hundred"
                        (if (zero? (mod x 100)) ""
                            (str " and " (convertNumToWord (mod x 100)))))

        (>= x 20) (str (nth tens (int (quot x 10)))
                       (if (zero? (mod x 10)) ""
                           (str " " (convertNumToWord (mod x 10)))))

        (> x 0) (nth nums x)))

;; Infinite list of nums startings from 1
(def num-list
  (iterate inc 1))

(defn solve 
  ([] (solve 1000))
  ([n]
  (reduce + (map (fn [x]
                   (count (filter (fn [char]
                                    (not (= char \space))) (convertNumToWord x))))
                 (take n (drop 1 (range)))))))

(defn solve-num-list
  ([] (solve-num-list 1000))
  ([n]
   (reduce + (map (fn [x] 
                    (count (filter (fn [char]
                                     (not (= char \space))) (convertNumToWord x))))
                  (take n num-list)))))

(comment
  (take 5 num-list)
  (take 5 (range))
  
  (solve)
  (solve-num-list) 
  )

