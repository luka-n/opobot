(ns opobot.imgur
  (:require
   [clj-http.client :as http]))

(def base-url "https://api.imgur.com/3/")

(def client-id (System/getenv "IMGUR_CLIENT_ID"))

(defn get-album [album-id]
  (http/get
   (str base-url "album/" album-id)
   {:as :json
    :headers
    {"Authorization" (str "Client-ID " client-id)}}))
