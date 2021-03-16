(ns traversal.generator-test
  (:require [clojure.test :refer :all]
            [traversal.generator :refer :all]))

(defn- count-vertices
  [graph]
  (count (set (concat (keys graph)
                      (map first (apply concat (vals graph)))))))

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
    (is (= (count-vertices (make-graph 2 1)) 2))
    (is (= (count-vertices (make-graph 3 2)) 3))
    (is (= (count-vertices (make-graph 3 3)) 3))
    (is (= (count-vertices (make-graph 3 4)) 3))
    (is (= (count-vertices (make-graph 3 5)) 3))
    (is (= (count-vertices (make-graph 3 6)) 3))))

(deftest sparseness-test
  (testing "Sparseness is correct"
    (is (= (count-edges (make-graph 2 1)) 1))
    (is (= (count-edges (make-graph 3 2)) 2))
    (is (= (count-edges (make-graph 3 3)) 3))
    (is (= (count-edges (make-graph 3 4)) 4))
    (is (= (count-edges (make-graph 3 5)) 5))
    (is (= (count-edges (make-graph 3 6)) 6))))

(deftest make-row-test
  (testing "Just a row"
    (is (= (convert-edges (make-init-row uno [1])) {}))
    (is (= (convert-edges (make-init-row uno [1 2])) {1 [[2 0]]}))
    (is (= (convert-edges (make-init-row uno [1 2 3])) {1 [[2 0]]
                                                              2 [[3 0]]}))))

(deftest make-graph-test
  (testing "Always first"
    (is (= (convert-edges (fill-graph uno first 1 0)) {}))
    (is (= (convert-edges (fill-graph uno first 2 1)) {:0 [[:1 0]]}))
    (is (= (convert-edges (fill-graph uno first 3 2)) {:0 [[:1 0]]
                                                       :1 [[:2 0]]}))
    (is (= (convert-edges (fill-graph uno first 3 3)) {:0 [[:2 0] [:1 0]]
                                                       :1 [[:2 0]]}))
    (is (= (convert-edges (fill-graph uno first 3 4)) {:0 [[:2 0] [:1 0]]
                                                       :1 [[:0 0] [:2 0]]}))
    (is (= (convert-edges (fill-graph uno first 3 5)) {:0 [[:2 0] [:1 0]]
                                                       :1 [[:0 0] [:2 0]]
                                                       :2 [[:0 0]]}))
    (is (= (convert-edges (fill-graph uno first 3 6)) {:0 [[:2 0] [:1 0]]
                                                       :1 [[:0 0] [:2 0]]
                                                       :2 [[:1 0] [:0 0]]})))
  (testing "Always last"
    (is (= (convert-edges (fill-graph uno last 1 0)) {}))
    (is (= (convert-edges (fill-graph uno last 2 1)) {:0 [[:1 0]]}))
    (is (= (convert-edges (fill-graph uno last 3 2)) {:0 [[:1 0]]
                                                      :1 [[:2 0]]}))
    (is (= (convert-edges (fill-graph uno last 3 3)) {:0 [[:1 0]]
                                                      :1 [[:2 0]]
                                                      :2 [[:1 0]]}))
    (is (= (convert-edges (fill-graph uno last 3 4)) {:0 [[:1 0]]
                                                      :1 [[:2 0]]
                                                      :2 [[:0 0] [:1 0]]}))
    (is (= (convert-edges (fill-graph uno last 3 5)) {:0 [[:1 0]]
                                                      :1 [[:0 0] [:2 0]]
                                                      :2 [[:0 0] [:1 0]]}))
    (is (= (convert-edges (fill-graph uno last 3 6)) {:0 [[:2 0] [:1 0]]
                                                      :1 [[:0 0] [:2 0]]
                                                      :2 [[:0 0] [:1 0]]}))))
