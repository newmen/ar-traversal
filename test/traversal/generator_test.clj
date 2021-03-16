(ns traversal.generator-test
  (:require [clojure.test :refer :all]
            [traversal.generator :refer :all]
            [traversal.utils :refer [get-vertices]]))

(defn- count-vertices
  [graph]
  (count (get-vertices graph)))

(defn- count-edges
  [graph]
  (count (apply concat (vals graph))))

(defn- convert-edges
  [graph]
  (into {}
        (map (fn [[k v]] [k (vec v)])
             graph)))

(defn- uno
  []
  0)

(deftest vertices-test
  (testing "Vertices number is correct"
    (is (= 2 (count-vertices (make-graph 2 1))))
    (is (= 3 (count-vertices (make-graph 3 2))))
    (is (= 3 (count-vertices (make-graph 3 3))))
    (is (= 3 (count-vertices (make-graph 3 4))))
    (is (= 3 (count-vertices (make-graph 3 5))))
    (is (= 3 (count-vertices (make-graph 3 6))))))

(deftest sparseness-test
  (testing "Sparseness is correct"
    (is (= 1 (count-edges (make-graph 2 1))))
    (is (= 2 (count-edges (make-graph 3 2))))
    (is (= 3 (count-edges (make-graph 3 3))))
    (is (= 4 (count-edges (make-graph 3 4))))
    (is (= 5 (count-edges (make-graph 3 5))))
    (is (= 6 (count-edges (make-graph 3 6))))))

(deftest make-row-test
  (testing "Just a row"
    (is (= {}
           (convert-edges (make-init-row uno [1]))))
    (is (= {1 [[2 0]]}
           (convert-edges (make-init-row uno [1 2]))))
    (is (= {1 [[2 0]]
            2 [[3 0]]}
           (convert-edges (make-init-row uno [1 2 3]))))))

(deftest make-graph-test
  (testing "Always first"
    (is (= {}
           (convert-edges (fill-graph uno first 1 0))))
    (is (= {:0 [[:1 0]]}
           (convert-edges (fill-graph uno first 2 1))))
    (is (= {:0 [[:1 0]]
            :1 [[:2 0]]}
           (convert-edges (fill-graph uno first 3 2))))
    (is (= {:0 [[:2 0] [:1 0]]
            :1 [[:2 0]]}
           (convert-edges (fill-graph uno first 3 3))))
    (is (= {:0 [[:2 0] [:1 0]]
            :1 [[:0 0] [:2 0]]}
           (convert-edges (fill-graph uno first 3 4))))
    (is (= {:0 [[:2 0] [:1 0]]
            :1 [[:0 0] [:2 0]]
            :2 [[:0 0]]}
           (convert-edges (fill-graph uno first 3 5))))
    (is (= {:0 [[:2 0] [:1 0]]
            :1 [[:0 0] [:2 0]]
            :2 [[:1 0] [:0 0]]}
           (convert-edges (fill-graph uno first 3 6)))))
  (testing "Always last"
    (is (= {}
           (convert-edges (fill-graph uno last 1 0))))
    (is (= {:0 [[:1 0]]}
           (convert-edges (fill-graph uno last 2 1))))
    (is (= {:0 [[:1 0]]
            :1 [[:2 0]]}
           (convert-edges (fill-graph uno last 3 2))))
    (is (= {:0 [[:1 0]]
            :1 [[:2 0]]
            :2 [[:1 0]]}
           (convert-edges (fill-graph uno last 3 3))))
    (is (= {:0 [[:1 0]]
            :1 [[:2 0]]
            :2 [[:0 0] [:1 0]]}
           (convert-edges (fill-graph uno last 3 4))))
    (is (= {:0 [[:1 0]]
            :1 [[:0 0] [:2 0]]
            :2 [[:0 0] [:1 0]]}
           (convert-edges (fill-graph uno last 3 5))))
    (is (= {:0 [[:2 0] [:1 0]]
            :1 [[:0 0] [:2 0]]
            :2 [[:0 0] [:1 0]]}
           (convert-edges (fill-graph uno last 3 6))))))
