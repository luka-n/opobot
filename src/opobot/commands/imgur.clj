(ns opobot.commands.imgur
  (:require
   [opobot.commands.core :refer [def-command]]
   [opobot.imgur :as imgur]
   [opobot.slack.rtm.connection :as rtm]))

(def-command #"^imgur (?<album>.+)" [channel matches]
  (let [album (.group matches "album")
        response (imgur/get-album album)
        image (:link (rand-nth (:images (:data (:body response)))))]
    (rtm/send-message channel image)))
