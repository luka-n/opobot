(ns opobot.sandbox
  (:require
   [clojail.core :as clojail]
   [clojail.testers :as testers]
   [clojure.stacktrace :as stacktrace])
  (:import java.io.StringWriter))

(def sandbox (clojail/sandbox testers/secure-tester :timeout 3000))

(defn execute-text [text]
  (try
    (with-open [writer (StringWriter.)]
      (let [bindings {#'*out* writer}
            result (sandbox (clojail/safe-read text) bindings)
            result-text (str result)]
        result-text))
   (catch Exception e (str (stacktrace/root-cause e)))))
