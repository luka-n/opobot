(ns opobot.commands.hello
  (:require
   [opobot.commands.core :refer [def-command]]
   [opobot.slack.rtm.connection :as rtm]))

(def-command #"^hello" [channel _]
  (rtm/send-message channel "hi!"))
