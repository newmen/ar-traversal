# Traversal test task from AR

This is just a done of test task for AR company.

## Usage

See `src/traversal/core.clj` :)

Below an example of usage:

```clojure
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

(get-eccentricity random-graph
                  (first (keys random-graph))) ;; => 102

(get-radius random-graph) ;; => 79

(get-diameter random-graph) ;; => 117
```
