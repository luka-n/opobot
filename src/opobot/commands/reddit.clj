(ns opobot.commands.reddit
  (:require
   [opobot.commands.core :refer [def-command]]
   [opobot.reddit :as reddit]
   [opobot.slack.rtm.connection :as rtm]))

(def-command #"^reddit (?<subreddit>.+)" [channel matches]
  (let [subreddit (.group matches "subreddit")
        response (reddit/posts subreddit)
        posts (:children (:data (:body response)))
        post (:data (rand-nth posts))]
    (rtm/send-message channel (:url post))))
