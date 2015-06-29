(defproject bonfire-server "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :min-lein-version "2.0.0"
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [compojure "1.3.1"]
                           [ring "1.4.0-RC1"]
                           [ring/ring-defaults "0.1.2"]
                           [org.clojure/java.jdbc "0.3.7"]
                           [org.postgresql/postgresql "9.2-1002-jdbc4"]
                           [ragtime "0.4.0"]
                           [yesql "0.4.0"]
                           [ring/ring-json "0.3.1"]]
            :plugins [[lein-ring "0.8.13"]]
            :ring {:handler bonfire-server.handler/app}
            :uberjar-name "bonfire-standalone.jar"
            :main ^:skip-aot bonfire-server.handler
            :profiles {:uberjar {:aot :all}
                       :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                            [ring-mock "0.1.5"]]}})
