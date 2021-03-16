(ns traversal.fixtures.graph)

(def graph
  {:a [[:b 100] [:e 50] [:d 30]]
   :b [[:c 20]]
   :d [[:e 10]]
   :e [[:c 5]]})
