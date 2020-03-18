(ns animBoard.core
  (:use
   arcadia.core
    arcadia.linear
    arcadia.debug)
  (:require
   [animBoard.base :as base]
   [animBoard.tiles :as tiles])
  (:import [UnityEngine Quaternion Resources Input KeyCode Vector3
            Time Transform]))

(defn init []
  (log "core.init: tile-0=" (object-named "tile-0"))
  (when-let [tiles (objects-named "tile-0")]
    (log "deleting tile-0, tiles=" tiles)
    (doall (map destroy-immediate tiles)))
  (tiles/init))

(init)
