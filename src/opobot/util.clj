(ns opobot.util
  (:require
   [clojure.string :as str]))

(defn unescape-html [text]
  (let [replacements {"&lt;" "<"
                      "&gt;" ">"
                      "&amp;" "&"}]
    (reduce #(apply str/replace %1 %2) text replacements)))
