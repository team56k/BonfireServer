(ns bonfire-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            [ring.middleware.json :as json-middleware]
            [bonfire-server.db :as db]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:use [ring.middleware.reload]
        [ring.middleware.params :only [wrap-params]]
        [clojure.pprint])
  (:gen-class))

(defroutes
  app-routes
  (POST "/event/create" [creator-id]
    (let [event (db/create-event<! db/db-spec creator-id)]
      {:status 200 :body event}))
  (POST "/echo" request
    (let [params (:params request)
          modified (if (nil? params) {} (assoc params :modified true))]
      {:status 200
       :body modified}))
  (route/not-found "Not Found"))

(defn debug-request [app]
  (fn [request]
    (println "Request:")
    (pprint request)
    (app request)))

(def app
  (-> (handler/site app-routes)
      (wrap-reload)
      (wrap-params)
      (debug-request)
      (json-middleware/wrap-json-response)))

(defn start [port]
  (ring/run-jetty app {:port port
                       :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (db/migrate)
    (start port)))
