(ns opobot.commands.muca
  (:require
   [opobot.commands.core :refer [def-command]]
   [opobot.macjahisa :as macjahisa]
   [opobot.slack.rtm.connection :as rtm]))

(def-command #"^muca" [channel _]
  (let [muce (macjahisa/muce)
        muca (rand-nth muce)]
    (rtm/send-message channel (:link muca))))
