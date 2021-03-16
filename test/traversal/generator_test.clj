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
    (let [make-and-count (comp count-vertices make-graph)]
      (is (= 2 (make-and-count 2 1)))
      (is (= 3 (make-and-count 3 2)))
      (is (= 3 (make-and-count 3 3)))
      (is (= 3 (make-and-count 3 4)))
      (is (= 3 (make-and-count 3 5)))
      (is (= 3 (make-and-count 3 6))))))

(deftest sparseness-test
  (testing "Sparseness is correct"
    (let [make-and-count (comp count-edges make-graph)]
      (is (= 1 (make-and-count 2 1)))
      (is (= 2 (make-and-count 3 2)))
      (is (= 3 (make-and-count 3 3)))
      (is (= 4 (make-and-count 3 4)))
      (is (= 5 (make-and-count 3 5)))
      (is (= 6 (make-and-count 3 6))))))

(deftest make-row-test
  (testing "Just a row"
    (let [make-row (comp convert-edges
                         (partial make-init-row uno))]
      (is (= {}
             (make-row [1])))
      (is (= {1 [[2 0]]}
             (make-row [1 2])))
      (is (= {1 [[2 0]]
              2 [[3 0]]}
             (make-row [1 2 3]))))))

(deftest make-graph-test
  (let [make-graph (comp convert-edges
                         (partial fill-graph identity uno))]
    (testing "Always first"
      (let [make-graph (partial make-graph first)]
        (is (= {}
               (make-graph 1 0)))
        (is (= {:0 [[:1 0]]}
               (make-graph 2 1)))
        (is (= {:0 [[:1 0]]
                :1 [[:2 0]]}
               (make-graph 3 2)))
        (is (= {:0 [[:2 0] [:1 0]]
                :1 [[:2 0]]}
               (make-graph 3 3)))
        (is (= {:0 [[:2 0] [:1 0]]
                :1 [[:0 0] [:2 0]]}
               (make-graph 3 4)))
        (is (= {:0 [[:2 0] [:1 0]]
                :1 [[:0 0] [:2 0]]
                :2 [[:0 0]]}
               (make-graph 3 5)))
        (is (= {:0 [[:2 0] [:1 0]]
                :1 [[:0 0] [:2 0]]
                :2 [[:1 0] [:0 0]]}
               (make-graph 3 6)))))
  (testing "Always last"
    (let [make-graph (partial make-graph last)]
      (is (= {}
             (make-graph 1 0)))
      (is (= {:0 [[:1 0]]}
             (make-graph 2 1)))
      (is (= {:0 [[:1 0]]
              :1 [[:2 0]]}
             (make-graph 3 2)))
      (is (= {:0 [[:1 0]]
              :1 [[:2 0]]
              :2 [[:1 0]]}
             (make-graph 3 3)))
      (is (= {:0 [[:1 0]]
              :1 [[:2 0]]
              :2 [[:0 0] [:1 0]]}
             (make-graph 3 4)))
      (is (= {:0 [[:1 0]]
              :1 [[:0 0] [:2 0]]
              :2 [[:0 0] [:1 0]]}
             (make-graph 3 5)))
      (is (= {:0 [[:2 0] [:1 0]]
              :1 [[:0 0] [:2 0]]
              :2 [[:0 0] [:1 0]]}
             (make-graph 3 6)))))))
