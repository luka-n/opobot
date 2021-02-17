(ns opobot.macjahisa
  (:require
   [clj-http.client :as http]
   [hickory.core :as h]
   [hickory.select :as s]))

(defn muce []
  (let [htree (-> (http/get "https://www.macjahisa.si/posvojitev/muce")
                  :body
                  h/parse
                  h/as-hickory)
        items (s/select (s/class "seznam-muc-list-item") htree)]
    (map
     (fn [item] {:link (str "https://www.macjahisa.si" (:href (:attrs item)))})
     items)))
