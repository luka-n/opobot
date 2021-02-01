(ns opobot.commands.core)

(def handlers [])

(defn find-handler [text]
  (let [handler (first
                 (filter
                  (fn [handler]
                    (let [re (get handler 0)
                          fn (get handler 1)]
                      (re-find re text)))
                  handlers))]
    (get handler 1)))

(defn register-handler [re fn]
  (def handlers (conj handlers [re fn])))

(defmacro def-command [name args body]
  (let [name (condp
                 =
                 (type name)
               clojure.lang.Symbol (re-pattern (str "^" (.toString name) "\\b"))
               java.util.regex.Pattern name)]
    `(register-handler ~name (fn ~args ~body))))
