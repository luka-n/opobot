(ns opobot.hooks.core)

(def hooks [])

(defn add-hook [fn]
  (def hooks (conj hooks fn)))

(defn run [msg]
  (doseq [hook hooks]
    (hook msg)))

(defmacro def-hook [args body]
  `(add-hook (fn ~args ~body)))
