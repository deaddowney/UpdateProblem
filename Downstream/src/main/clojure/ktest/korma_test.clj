(ns ktest.korma-test
  (:refer-clojure :exclude [test])
  (:import [java.util Date])
  (:require [korma.core :as k]))

;entity functions for writing & reading tests

(declare test-run test message validation)

(k/defentity my-table
             (k/table :my-table))

(defn write-test-run [test-run]
  (if-let [tr test-run]
    (let [r-id (k/insert my-table
                         (k/values {:env        (:env tr)
                                    :start_time (java.sql.Timestamp. 1234)
                                    }
                                   ))]
      (doseq [result (:results tr)]

        (let [t-id (k/insert my-table
                             (k/values {:name        (str (:name result))
                                        :passed      (:pass result)
                                        :error_msg   (:reason result)
                                        :test_run_id (:generated_key r-id)}))
              validations-by-msg (group-by :failed-msg (:validations result))]
          (doseq [msg (:data result)]
            (let [mg (first (vals msg))
                  m-id (k/insert my-table
                                 (k/values
                                   {:test_id  (:generated_key t-id)
                                    :incoming (not (nil? (:received msg)))
                                    :data     "foo"}))]
              (when-let [bad-validations (get validations-by-msg mg)]
                (doseq [bad-validation bad-validations
                        error-msg (into #{} (:failures bad-validation))] ;some validations are dupes, so put them in a set
                  (try
                    (k/insert my-table
                              (k/values {:message_id (:generated_key m-id)
                                         :data       error-msg}))
                    (catch Exception e
                      (throw (IllegalArgumentException. (str "Could not insert validation " error-msg) e)))))
                ))

            ))
        )
      ;update the end-time
      (k/update my-table
                (k/set-fields {:end_time (java.sql.Timestamp. (.getTime (Date.)))})
                (k/where {:id (:generated_key r-id)}))
      (println "Done with test-run!")
      r-id
      )))

(defn update-table[]
  (k/update my-table
          (k/set-fields {:end_time (java.sql.Timestamp. (.getTime (Date.)))})))

