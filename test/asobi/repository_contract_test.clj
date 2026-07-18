(ns asobi.repository-contract-test
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is testing]]))

(def canonical-edn
  ["manifest.edn" "identity.edn" "dependencies.edn" "repository-contracts.edn"
   "kotoba.app.edn" "data/seed-asobi-graph.kotoba.edn"
   "schema/schema.edn" "schema/asobi-ontology.kotoba.edn"])

(deftest canonical-metadata-is-readable-edn
  (doseq [path canonical-edn]
    (testing path
      (is (some? (edn/read-string (slurp (io/file path))))))))

(deftest legacy-layout-is-absent
  (doseq [path ["actor.edn" "manifest.jsonld" "run_tests.sh" "methods/publish.bb"]]
    (is (not (.exists (io/file path))) path)))

(deftest external-json-is-wire-only
  (let [root (.getCanonicalFile (io/file "."))
        files (file-seq root)
        jsons (filter #(and (.isFile %)
                            (re-find #"\\.(json|jsonld)$" (.getName %)))
                      files)]
    (doseq [f jsons]
      (let [rel (str (.relativize (.toPath root) (.toPath f)))]
        (is (or (.startsWith rel "wire/")
                (= rel ".well-known/did.json"))
            rel)))))

(deftest deprecated-language-artifacts-are-absent
  (let [paths (map #(.getName %) (filter #(.isFile %) (file-seq (io/file "."))))]
    (is (not-any? #(re-find #"\\.(go|tinygo)$" %) paths))))
