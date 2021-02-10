(ns opobot.commands.cat
  (:require
   [clj-http.client :as http]
   [clojure.string :as str]
   [opobot.commands.core :refer [def-command]]
   [opobot.imgur :as imgur]
   [opobot.slack.rtm.connection :as rtm]))

(def cat-albums
  {"5m8iBZq" [":opomuc:" "cefur" "muc" "muuc" "muuuc" "muuuuc" "muuuuuc"
              "muuuuuuc" "muuuuuuuc" "muuuuuuuuc" "muuuuuuuuuc" "muuuuuuuuuuc"
              "muuuuuuuuuuuc" "opomuc"]
   "FLOxSG3" ["ciri" "cric" "cricek" "nichi"]
   "HJAUs1m" [":bongo_silence:" "silence" "silenec"]
   "gYWlrwp" [":b:inky" "binky" "ink" "inky" "tripod"]
   "nVCMPIl" ["satan" "stan"]})

(defn find-album [cat]
  (first (first (filter (fn [[k v]] (some #(= cat %) v)) cat-albums))))

(def command-re
  (re-pattern
   (str
    "^(?<cat>" (str/join "|" (flatten (map second cat-albums))) ")")))

(def-command command-re [channel matches]
  (let [cat (.group matches "cat")
        album (find-album cat)
        response (imgur/get-album album)
        image (:link (rand-nth (:images (:data (:body response)))))]
    (rtm/send-message channel image)))
