(ns opobot.commands.run
  (:require
   [opobot.commands.core :refer [def-command]]
   [opobot.sandbox :refer [execute-text]]
   [opobot.slack.rtm.connection :as rtm]
   [opobot.util :refer [unescape-html]]))

(def-command #"^run (?<code>.+)" [channel matches]
  (let [code (unescape-html (.group matches "code"))
        result (execute-text code)]
    (rtm/send-message channel result)))
