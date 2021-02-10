(ns opobot.commands.core
  (:require
   [clojure.string :as str]
   [opobot.slack.rtm.connection :as rtm]))

(def commands [])

(defn add-command [re fn]
  (def commands (conj commands {:re re :fn fn})))

(defn find-command [text]
  (first
   (filter
    (fn [command]
      (.find (doto (re-matcher (:re command) text) (.find)) 0))
    commands)))

(defn invoke [channel text]
  (let [command (find-command text)]
    (if command
      (let [re (:re command)
            fn (:fn command)]
        (fn channel (doto (re-matcher re text) (.find))))
      (rtm/send-message channel "what"))))

(defn run [msg]
  (let [channel (get msg "channel")
        text (get msg "text")]
    (when (re-find (re-pattern (str "^" rtm/name "\\b")) text)
      (invoke
       channel
       (str/replace
        text
        (re-pattern (str "^" rtm/name "\\b\\s*"))
        "")))))

(defmacro def-command [re args body]
  `(add-command ~re (fn ~args ~body)))
