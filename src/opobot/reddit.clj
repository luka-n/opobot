(ns opobot.reddit
  (:require
   [clj-http.client :as http]))

(defn posts [subreddit]
  (http/get
   (str "https://www.reddit.com/r/" subreddit ".json")
   {:as :json
    :headers {"User-Agent" "(opo)"}}))
