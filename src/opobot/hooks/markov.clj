(ns opobot.hooks.markov
  (:require
   [opobot.hooks.core :refer [def-hook]]
   [opobot.markov :as markov]))

(def-hook [msg]
  (let [text (get msg "text")]
    (markov/parse text)))
