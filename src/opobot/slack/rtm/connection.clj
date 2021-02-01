(ns opobot.slack.rtm.connection
  (:require
   [cheshire.core :as json]
   [gniazdo.core :as ws]
   [opobot.slack.web.rtm :as rtm]))

(def listener nil)

(def name nil)

(defn send-message [channel text]
  (ws/send-msg
   listener
   (json/generate-string
    {:type "message"
     :channel channel
     :text text})))

(defn start [token & {:keys [on-receive]}]
  (let [response (rtm/connect token)
        body (:body response)
        new-name (:name (:self body))
        url (:url body)]
    (let [new-listener (ws/connect url :on-receive on-receive)]
      (do
        (def listener new-listener)
        (def name new-name)))))
