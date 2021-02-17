(ns opobot.commands.youtube
  (:require
   [opobot.commands.core :refer [def-command]]
   [opobot.slack.rtm.connection :as rtm]
   [opobot.youtube :as youtube]))

(defn youtube-link [video-id]
  (str "https://www.youtube.com/watch?v=" video-id))

(def-command #"^youtube (?<query>.+)" [channel matches]
  (let [query (.group matches "query")
        response (youtube/search query)
        video (first (:items (:body response)))]
    (rtm/send-message channel (youtube-link (:videoId (:id video))))))
