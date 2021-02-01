(ns opobot.slack.commands.hello
  (:require
   [opobot.commands.core :refer [def-command]]
   [opobot.slack.rtm.connection :as rtm]))

(def-command hello [channel]
  (rtm/send-message channel "hi!"))
