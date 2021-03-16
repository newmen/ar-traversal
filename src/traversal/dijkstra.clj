(ns traversal.dijkstra
  (:require [traversal.utils :refer [get-vertices]]))

(def max-value
  Integer/MAX_VALUE)

(defn- init-path
  [start v]
  [v [[start max-value]]])

(defn- sum-path
  [path]
  (apply + (map last path)))

(defn find-shorts
  [graph start]
  (let [vertices (get-vertices graph)
        paths (-> (into {} (map (partial init-path start) vertices))
                  (assoc start [[start 0]]))]
    (loop [vs (set vertices)
           ps paths]
      (if (empty? vs)
        ps
        (let [v (apply (partial min-key (comp sum-path ps)) vs)
              path (v ps)
              w (sum-path path)
              neighbours (into {} (v graph))]
          (recur (disj vs v)
                 (into {}
                       (map (fn [[vi pi]]
                              (let [wn (vi neighbours)]
                                (if (and wn (< (+ w wn) (sum-path pi)))
                                  [vi (conj path [vi wn])]
                                  [vi pi])))
                            ps))))))))

(defn find-short
  [graph start end]
  (let [shorts (find-shorts graph start)]
    (if-let [path (end shorts)]
      (map first path)
      :no-path)))

(defn dijkstra
  [graph start]
  (let [shorts (find-shorts graph start)]
    (into {}
          (map (fn [[v path]]
                 [v (sum-path path)])
               shorts))))
(comment

  (dijkstra {:a [[:b 100] [:e 50] [:d 30]]
             :b [[:c 20]]
             :d [[:e 10]]
             :e [[:c 5]]} :b)
  
  )
