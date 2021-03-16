(ns traversal.dijkstra-test
  (:require [clojure.test :refer :all]
            [traversal.dijkstra :refer :all]
            [traversal.fixtures.graph :refer [graph]]))

(deftest find-shorts-test
  (testing "Result has paths"
    (is (= {:a [[:a 0]]
            :b [[:a 0] [:b 100]]
            :c [[:a 0] [:d 30] [:e 10] [:c 5]]
            :d [[:a 0] [:d 30]]
            :e [[:a 0] [:d 30] [:e 10]]}
           (find-shorts graph :a)))))

(deftest find-short-test
  (testing "Result is a shortest path"
    (is (= [:a :d :e :c]
           (vec (find-short graph :a :c))))))

(deftest dijkstra-test
  (testing "Result has just weights"
    (is (= {:a 0
            :b 100
            :c 45
            :d 30
            :e 40}
           (dijkstra graph :a)))))
