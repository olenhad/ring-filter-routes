(ns ring.middleware.filter-routes)

(defn- traverse-filters [req filters]
  (reduce
   (fn [acc m]
     (cond
      (not (nil? acc))
      acc
      (and (= (req :uri)  (m :url))
           (not ((m :check))))
      (m :else-action)
      :else
      nil))
   nil
   filters
   ))

(defn wrap-filter-routes [app filters]
  (fn [req]
    (let [action (traverse-filters req filters)]
      (if action
        (action)
        (app req)))))
