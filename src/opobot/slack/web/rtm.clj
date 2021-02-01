(ns opobot.slack.web.rtm
  (:require
   [clj-http.client :as http]))

(defn connect [token]
  (http/get
   "https://slack.com/api/rtm.connect"
   {:as :json
    :query-params {"token" token}}))
