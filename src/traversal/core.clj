(ns traversal.core
  (:require [traversal.generator :refer [make-graph]]
            [traversal.dijkstra :refer [find-shortest]]
            [traversal.distance :as distance]))

(comment

  (def random-graph (make-graph 5 15))
  ;; =>
  ;; {:4 ([:3 36] [:2 60] [:1 6])
  ;;  :1 ([:4 38] [:3 51])
  ;;  :3 ([:1 87] [:2 16] [:0 67])
  ;;  :0 ([:4 61] [:1 88] [:2 97])
  ;;  :2 ([:0 50] [:3 37] [:4 79] [:1 76])}

  (find-shortest random-graph
                 (first (keys random-graph))
                 (last (keys random-graph))) ;; => (:4 :3 :2)

  (distance/get-eccentricity random-graph
                             (first (keys random-graph))) ;; => 102

  (distance/get-radius random-graph) ;; => 79

  (distance/get-diameter random-graph) ;; => 117
  
  )
