(ns opobot.commands.image
  (:require
   [opobot.commands.core :refer [def-command]]
   [opobot.customsearch :as cs]
   [opobot.slack.rtm.connection :as rtm]))

(def-command #"^image (?<query>.+)" [channel matches]
  (let [query (.group matches "query")
        response (cs/search query {"searchType" "image"})
        items (:items (:body response))
        image (rand-nth items)]
    (rtm/send-message channel (:link image))))
