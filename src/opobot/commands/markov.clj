(ns opobot.commands.markov
  (:require
   [opobot.commands.core :refer [def-command]]
   [opobot.markov :as markov]
   [opobot.slack.rtm.connection :as rtm]))

(def-command #"^markov( (?<starter>.+))?" [channel matches]
  (let [starter (.group matches "starter")
        sentence (markov/sentence starter)]
    (rtm/send-message channel sentence)))

(def-command #"^(what is your )?wisdom" [channel _]
  (let [sentence (markov/sentence)]
    (rtm/send-message channel sentence)))
