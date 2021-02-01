(ns opobot.core
  (:gen-class)
  (:require
   [cheshire.core :as json]
   [opobot.commands.core :as commands]
   [opobot.commands.hello]
   [opobot.slack.rtm.connection :as rtm]))

(def token (System/getenv "OPOBOT_TOKEN"))

(defn on-rtm-receive [msg]
  (let [data (json/parse-string msg)]
    (if (= "message" (get data "type"))
      (let [channel (get data "channel")
            text (get data "text")
            handler (commands/find-handler text)]
        (if handler
          (handler channel)
          (rtm/send-message channel "what"))))))

(defn -main
  [& args]
  (rtm/start token :on-receive on-rtm-receive))
