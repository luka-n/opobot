(ns opobot.markov
  (:require
   [cheshire.core :as json]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def dictionary-path "dict.json")

(def max-depth 3)

(def max-sentence-length 32)

(def sentence-ender-re #"[.?!]")

(def sentence-splitter-re #"[^\s\p{P}]+|\p{P}")

(def word-exclusion-re #"[\p{Ps}\p{Pe}\p{Pi}\p{Pf}]")

(defmacro with-dictionary [args body]
  `(do
     (when-not (.exists (io/file dictionary-path))
       (spit dictionary-path (json/generate-string {})))
     ((fn ~args ~body) (json/parse-string (slurp dictionary-path)))))

(defn add-word [a b]
  (with-dictionary [dict]
    (spit
     dictionary-path
     (json/generate-string (update-in dict [a b] #(inc (or % 0)))))))

(defn cleanup-sentence [sentence]
  (let [replacements {" ," ","
                      " ." "."
                      " ?" "?"
                      " !" "!"}]
    (loop [s sentence
           rs replacements]
      (let [replacement (first rs)]
        (if replacement
          (recur
           (str/replace s (first replacement) (second replacement))
           (next rs))
          (str/trim s))))))

(defn excluded-word? [word]
  (re-matches word-exclusion-re word))

(defn next-word [starter]
  (with-dictionary [dict]
    (let [candidates (get dict starter)
          sum (reduce + (map second candidates))]
      (if candidates
        (loop [cs candidates
               rnd (rand sum)]
          (let [candidate (first cs)]
            (if (< rnd (second candidate))
              (first candidate)
              (recur (next cs) (- rnd (second candidate))))))
        nil))))

(defn parse [text]
  (let [words (re-seq sentence-splitter-re text)]
    (doseq [depth (range 1 (+ 1 max-depth))]
      (doseq [pair (partition (+ 1 depth) 1 words)]
        (let [a (str/join " " (take depth pair))
              b (nth pair depth)]
          (when-not (or (excluded-word? a)
                        (excluded-word? b))
            (add-word a b)))))))

(defn random-word []
  (with-dictionary [dict] (rand-nth (keys dict))))

(defn sentence-ender? [word]
  (re-matches sentence-ender-re word))

(defn sentence [& [starter]]
  (let [starter (or starter (random-word))]
    (cleanup-sentence
     (str/join
      " "
      (loop [words [starter]
             i 0
             new-word (next-word starter)]
        (if (and new-word
                 (< i max-sentence-length)
                 (not (sentence-ender? new-word)))
          (recur (conj words new-word)
                 (+ 1 i)
                 (next-word new-word))
          (conj words new-word)))))))
