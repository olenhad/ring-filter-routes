(ns ring.middleware.filter-routes-test
  (:use clojure.test
        ring.middleware.filter-routes))

(deftest basic
  (testing "FIXME, I fail."
    (let [filters [{:url "/pass"
                    :check (fn [] true)
                    :else-action (fn [] "fucked up")}
                   {:url "/shallnotpass"
                    :check (fn [] false)
                    :else-action (fn [] "gandalf")}
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

(def app-filters
  [{:url "/moria"
    :check (fn [] (= (session/current-user) :balrog))
    :else-action (fn [] (ring/redirect "/shallnotpass"))}

   {:url "/winterfell"
    :check (fn [] (= (westeros/season) :winter))
    :else-action (fn [] (do (println "Winter is Coming...")
                           (ring/redirect "/season1")))}])
