(ns traversal.distance-test
  (:require [clojure.test :refer :all]
            [traversal.distance :refer :all]
            [traversal.fixtures.graph :refer [graph]]))

(deftest get-eccentricity-test
  (testing "Eccentricity of each vertex"
    (is (= 100 (get-eccentricity graph :a)))
    (is (= 20 (get-eccentricity graph :b)))
    (is (= 0 (get-eccentricity graph :c)))
    (is (= 15 (get-eccentricity graph :d)))
    (is (= 5 (get-eccentricity graph :e)))))

(deftest get-radius-test
  (testing "The radius of a graph is the minimum eccentricity of any vertex in a graph"
    (is (= 0 (get-radius graph)))))

(deftest get-diameter-test
  (testing "The diameter of a graph is the maximum eccentricity of any vertex in a graph"
    (is (= 100 (get-diameter graph)))))
