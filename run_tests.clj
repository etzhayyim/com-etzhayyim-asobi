(babashka.classpath/add-classpath "src:test")
(require '[clojure.test :as t])

(def suites '[asobi.methods.test-datom-emit
              asobi.methods.test-ie-flow
              asobi.murakumo-test
              asobi.tests.test-analyze
              asobi.tests.test-coverage
              asobi.tests.test-kotoba
              asobi.tests.test-opening-priority
              asobi.repository-contract-test])
(apply require suites)
(let [{:keys [fail error]} (apply t/run-tests suites)]
  (System/exit (if (zero? (+ fail error)) 0 1)))
