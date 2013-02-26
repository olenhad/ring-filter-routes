(ns ring.middleware.filter-routes-test
  (:use clojure.test
        ring.middleware.filter-routes))

(deftest basic
  (testing "FIXME, I fail."
    (let [filters [{:url "/pass"
                    :check (fn [req] true)
                    :else-action (fn [req] "fucked up")}
                   {:url "/shallnotpass"
                    :check (fn [req] false)
                    :else-action (fn [req] "gandalf")}

                   ]
          req1 {:uri "/pass"}
          req2 {:uri "/shallnotpass"}
          app #(str "Lothlorien with" (:uri %))]
      (is (=
           ((wrap-filter-routes app filters) req1))
          "Lothlorien with /pass"
          )
      (is (=
           ((wrap-filter-routes app filters) req2)
           "gandalf")))))
