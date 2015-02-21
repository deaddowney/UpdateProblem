(ns deps.domain
  (:refer-clojure :exclude [test])
  (:require [korma.core :as k])
  )

;entity functions for writing & reading tests

(declare test-run test message validation)

(k/defentity test-run
             (k/table :test_run)
             (k/has-many test))

(k/defentity test
             (k/belongs-to test-run)
             (k/has-many message))

(k/defentity message
             (k/belongs-to test)
             (k/has-many validation))

(k/defentity validation
             (k/belongs-to test))



