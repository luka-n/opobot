(ns opobot.customsearch
  (:require
   [clj-http.client :as http]
   [ring.util.codec :refer [url-encode]]))

(def url "https://www.googleapis.com/customsearch/v1")

(def cx (System/getenv "CUSTOMSEARCH_CX"))
(def key (System/getenv "CUSTOMSEARCH_KEY"))

(defn search [query & [query-params]]
  (http/get
   url
   {:as :json
    :query-params
    (merge
     {"cx" cx
      "key" key
      "q" (url-encode query)}
     query-params)}))
