(ns traversal.generator)

(defn make-vertices
  [n]
  (map (comp keyword str)
       (range n)))

(defn add-edge
  ([wf graph [from to]]
   (add-edge wf graph from to))
  ([wf graph from to]
   (update graph from conj [to (wf)])))

(defn make-init-row
  [wf vertices]
  (let [pairs (partition 2 1 vertices)]
    (reduce (partial add-edge wf) {} pairs)))

(defn fill-graph
  [wf rf n s]
  (let [vertices (make-vertices n)
        t (dec n)]
    (loop [i (- s t)
           vs vertices
           graph (make-init-row wf vertices)]
      (if (zero? i)
        graph
        (let [v (rf vs)
              edges (v graph)
              other (remove #{v} vertices)
              uncons (remove (set (map first edges)) other)
              next-vs (if (= t (inc (count edges)))
                        (remove #{v} vs)
                        vs)]
          (recur (dec i)
                 next-vs
                 (add-edge wf graph v (rf uncons))))))))

(defn- error
  [& args]
  (throw (Exception. (apply str args))))

(defn make-graph
  [n s]
  (let [t (dec n)]
    (cond
      (or (< n 0)
          (< s 0)) (error "Both graph size (" n ")"
                          " and sparseness (" s ")"
                          " must be positive")
      (or (< s t)
          (> s (* n t))) (error "Incorrect sparseness " s
                                " of graph with " n " vertices")
      :else (fill-graph #(rand-int 100)
                        rand-nth
                        n s))))
