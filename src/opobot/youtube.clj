(ns opobot.youtube
  (:require
   [clj-http.client :as http]))

(def api-key (System/getenv "YOUTUBE_API_KEY"))

(defn search [query]
  (http/get
   "https://www.googleapis.com/youtube/v3/search"
   {:as :json
    :query-params
    {:key api-key
     :part "snippet"
     :q query}}))
