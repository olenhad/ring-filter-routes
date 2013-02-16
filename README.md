# ring-filter-routes
Very simple route filtering middleware
To use, add to your project's dependencies:

```clojure
:dependencies [[org.clojure/clojure "1.4.0"]
               [ring-filter-routes "0.1.1"]]
```

## Usage
Takes a vector of filter rules. Each rule is a hash-map of :url a :check function and an :else-action to execute if the check fails.
For instance

```clojure
(def app-filters
  [{:url "/moria"
    :check (fn [] (= (session/current-user) :balrog))
    :else-action (fn [] (ring/redirect "/shallnotpass"))}

   {:url "/winterfell"
    :check (fn [] (= (westeros/season) :winter))
    :else-action (fn [] (do (println "Winter is Coming...")
                           (ring/redirect "/season1")))}])
;in your app def just add
(def app
  (-> (handler/site main-routes)
      (wrap-base-url)
      (wrap-filter-routes app-filters)))

```

## License

Copyright © 2013 Omer Iqbal

Distributed under the Eclipse Public License, the same as Clojure.
