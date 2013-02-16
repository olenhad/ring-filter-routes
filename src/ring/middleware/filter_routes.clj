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
  "middleware for adding filters to routes. filters is a vector of hashmaps containing :url :check and :else-action. :url is the obviously the url which is checked for. :check should be a function that returns a truthy/falsey value. :else-action is executed if :check is NOT satisfied"
  (fn [req]
    (let [action (traverse-filters req filters)]
      (if action
        (action)
        (app req)))))
