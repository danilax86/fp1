# Лабораторная работа #1

### Дисциплина 
Функциональное программирование

### Выполнил 
Данила Горелко, P34102

### Цель работы
Решить две задачи project euler, различными способами используя выбранный ранее функциональный ЯП.
Освоить базовые приёмы и абстракции функционального программирования: функции, поток управления и поток данных,
сопоставление с образцом, рекурсия, свёртка, отображение, работа с функциями как с данными, списки.

### Вариант

| ЯП | Clojure |
|------| ------- |
| Задания | 10, 21|
| ЯП по выбору | Python |

### Требования

1. монолитные реализации с использованием:
   * хвостовой рекурсии
   * рекурсии (вариант с хвостовой рекурсией не является примером рекурсии)
2. модульной реализации, где явно разделена генерация последовательности, фильтрация 
и свёртка (должны использоваться функции reduce, fold, filter и аналогичные)
3. генерация последовательности при помощи отображения (map)
4. работа со спец. синтаксисом для циклов (где применимо)
5. работа с бесконечными списками для языков поддерживающих ленивые коллекции или итераторы как часть языка (к примеру Haskell, Clojure)
6. реализация на любом удобном для вас традиционном языке программировании для сравнения.

### Условия задач

#### Задача 14
<p>The following iterative sequence is defined for the set of positive integers:</p>
<ul style="list-style-type:none;">
<li>$n \to n/2$ ($n$ is even)</li>
<li>$n \to 3n + 1$ ($n$ is odd)</li></ul>
<p>Using the rule above and starting with $13$, we generate the following sequence:
$$13 \to 40 \to 20 \to 10 \to 5 \to 16 \to 8 \to 4 \to 2 \to 1.$$</p>
<p>It can be seen that this sequence (starting at $13$ and finishing at $1$) contains $10$ terms. Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at $1$.</p>
<p>Which starting number, under one million, produces the longest chain?</p>
<p class="note"><b>NOTE:</b> Once the chain starts the terms are allowed to go above one million.</p>
  
#### Задача 17
<p>If the numbers $1$ to $5$ are written out in words: one, two, three, four, five, then there are $3 + 3 + 5 + 4 + 4 = 19$ letters used in total.</p>
<p>If all the numbers from $1$ to $1000$ (one thousand) inclusive were written out in words, how many letters would be used? </p>
<br><p class="note"><b>NOTE:</b> Do not count spaces or hyphens. For example, $342$ (three hundred and forty-two) contains $23$ letters and $115$ (one hundred and fifteen) contains $20$ letters. The use of "and" when writing out numbers is in compliance with British usage.</p>


### Ход работы

#### Настройка проекта
deps.edn:
```edn
{:paths ["src"]
 :deps {org.clojure/clojure {:mvn/version "1.10.3"}}
 :aliases {:test {:extra-paths ["test"]
                  :extra-deps  {lambdaisland/kaocha {:mvn/version "0.0-529"}}
                  :main-opts   ["-m" "kaocha.runner"]}}}
```

#### Yaml
```yml
name: Example workflow

on: [push]

jobs:
  clojure:
    strategy:
      matrix:
        os: [ubuntu-latest]

    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v3

      # It is important to install java before installing clojure tools which needs java
      # exclusions: babashka, clj-kondo and cljstyle
      - name: Prepare java
        uses: actions/setup-java@v3
        with:
          distribution: 'zulu'
          java-version: '8'

      - name: Install clojure tools
        uses: DeLaGuardo/setup-clojure@12.1
        with:
          cli: 1.10.1.693              # Clojure CLI based on tools.deps
          clj-kondo: 2022.05.31        # Clj-kondo
          cljfmt: 0.10.2               # cljfmt

      - name: clj-fmt fix
        run: cljfmt fix src test

      - name: clj-kondo checks
        run: clj-kondo --lint src test

      - name: run-tests
        run: clojure -Mtest
```

#### Тесты
```clj
(ns problem-14-test
  (:require [clojure.test :refer [deftest testing is]]
            [problem-14 :as p]))

(def answer 837799)

(deftest test-14
  (testing "Recursion"
    (is (= answer (p/solve-rec)))
    (is (= answer (p/solve-rec-recur))))
  (testing "Generation"
    (is (= answer (p/solve-gfr)))
    (is (= answer (p/solve-gm))))
  (testing "Special"
    (is (= answer (p/solve-lazy)))))
```

```clj
(ns problem-17-test
  (:require [clojure.test :refer [deftest testing is]]
            [problem-17 :as p]))

(def answer 21124)

(deftest test-17
  (testing "General"
    (is (= answer (p/solve)))
    (is (= answer (p/solve-num-list)))))
```

#### 14 задача
```clj
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
   (let [tuple (fn [n] [n (length-rec n)]) 
         maxf (fn [a b] (let [[_ a_second] a 
                              [_ b_second] b]
             (if (> a_second b_second) a b)))]
     (first (reduce maxf (map tuple (range 1 n)))))))


(defn solve-rec-recur
  ([] (solve-rec-recur 1000000))
  ([n]
   (let [tuple (fn [n] [n (count (colatz-seq n))]) 
         maxf (fn [a b] (let [[_ a-second] a 
                              [_ b-second] b]
                (if (> a-second b-second) a b)))]
     (first (reduce maxf (map tuple (range 1 n)))))))


;; 2. Gen, filter, reduce
(defn solve-gfr
   ([] (solve-gfr 1000000))
   ([n]
    (first (reduce (fn [x y] (let [[_ x_second] x 
                                   [_ y_second] y]
                        (if (> x_second y_second) x y)))
            (for [n (range 1 (inc n))]
              [n (length-rec n)])))))


;; 3. Generation with map
(defn solve-gm
  ([] (solve-gm 1000000))
   ([n]
    (first (reduce (fn [x y] (let [[_ x_second] x
                                   [_ y_second] y]
                     (if (> x_second y_second) x y)))
                   (for [n (map inc (take n (range)))]
                     [n (length-rec n)])))))


;; 5. Lazy collections
(defn solve-lazy
  ([] (solve-lazy 1000000))
  ([n]
   (apply max-key length-rec (range 1 n))))
```
#### 17 задача
```clj
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
```

### Вывод
В ходе выполнения данной лабораторной работы я познакомился с основами языка Clojure.
Научился базовым операциям с последовательностями, а именно генерации, фильтрации и свёртки.
