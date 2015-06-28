(ns bonfire-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:use [ring.middleware.reload]
        [clojure.pprint])
  (:gen-class))

(defroutes app-routes
  (POST "/echo" request
    (let [_ (pprint request)
          body (:body request)
          modified (if (nil? body) {} (assoc body :modified true))]
      {:status 200
       :body modified}))
  (route/not-found "Not Found"))

(defn my-middleware [app]
  (fn [request]
    ;; This is where you'd do any processing on the request
    ;; Finally, keep the chain going by calling app
    (println "Early middleware")
    (pprint request)
    (app request)))

(def app
  (-> (handler/site app-routes)
      (wrap-reload)
      (my-middleware)
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response))

(defn start [port]
  (ring/run-jetty app {:port port
                       :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
