(ns traversal.distance
  (:require [traversal.dijkstra :refer [dijkstra max-value]]
            [traversal.utils :refer [get-vertices]]))

(defn get-eccentricity
  [graph v]
  (->> (dijkstra graph v)
       (map last)
       (remove #{max-value})
       (apply max)))

(defn- get-by-f
  [f graph]
  (->> (get-vertices graph)
       (map (partial get-eccentricity graph))
      ;;  (remove #{0}) ;; must i filter it for the radius searching? :)
       (apply f)))

(def get-radius
  (partial get-by-f min))

(def get-diameter
  (partial get-by-f max))
