(ns traversal.utils)

(defn get-vertices
  [graph]
  (set (concat (keys graph)
               (map first (apply concat (vals graph))))))
