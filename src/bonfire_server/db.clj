(ns bonfire-server.db
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]
            [yesql.core :refer [defqueries]]))

(def db-spec (or (System/getenv "DATABASE_URL") "postgresql://localhost:5432/bonfire-dev"))

(def migration-config
  {:database (jdbc/sql-database db-spec)
   :migrations (jdbc/load-resources "migrations")})

(defn migrate [] (repl/migrate migration-config))

(def queries (defqueries "queries/queries.sql"))
