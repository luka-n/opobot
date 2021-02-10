(ns opobot.core
  (:gen-class)
  (:require
   [cheshire.core :as json]
   [opobot.commands.cat]
   [opobot.commands.core :as commands]
   [opobot.commands.hello]
   [opobot.commands.image]
   [opobot.commands.markov]
   [opobot.hooks.core :as hooks]
   [opobot.hooks.markov]
   [opobot.slack.rtm.connection :as rtm]))

(def token (System/getenv "OPOBOT_TOKEN"))

(defn normalize-commmand-text [text])

(defn on-rtm-receive [msg]
  (let [data (json/parse-string msg)]
    (when (and (= "message" (get data "type"))
               (not (get data "subtype")))
      (commands/run data)
      (hooks/run data))))

(defn -main
  [& args]
  (rtm/start token :on-receive on-rtm-receive))
