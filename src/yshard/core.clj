(ns yshard.core
  (:require [clojure.tools.cli :as cli]
            [clojure.java.io   :as io]
            [clojure.string    :as string]))


(def cli-options
  [["-h" "--help" "Show this help text"]
   ["-g" "--groupby" "JSON Path expression to group by" :default "." :parse-fn #(str %)]
   "-d" "--destination" "Destination directory for output files" :default "." :parse-fn #(str %)])

(defn usage
  [options-summary]
  (string/join
    \newline
    ["Groups yaml files in the input folder by"
     "the specified jsonpath"
     ""
     "Usage: yshard [OPTIONS] SOURCE DESTINATION"
     ""
     "Options:"
     options-summary]))

(defn parse-arguments
  "Parse and validate command line arguments. Either return a map
  indicating the program should exit (with a error message, and
  optional ok status), or a map indicating the action the program
  should take and the options provided."
  [args]
  (let [{:keys [arguments options errors summary]} (cli/parse-opts args cli-options)]
    (cond
      (:help options)              {:exit-message (usage summary) :ok? true}
      errors                       {:exit-message errors}
      :else                        {:options      options})))

(defn -main
  [& args]
  (println (parse-arguments args)))
