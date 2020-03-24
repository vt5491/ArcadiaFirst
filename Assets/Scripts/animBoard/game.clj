(ns animBoard.game
  (:use
   arcadia.core
    arcadia.linear
    arcadia.debug)
  (:require
   [animBoard.base :as base]
   [animBoard.tiles :as tiles]
   [animBoard.player :as player]
   [animBoard.anim :as anim])
  (:import [UnityEngine Quaternion Resources Input KeyCode Vector3
            Time Transform]))

;; evaluation sequence
;; base.clj
;; tiles.clj
;; player.clj
;; anim.clj
;; game.clj

(defn init []
  (log "core.init: tile-0=" (object-named "tile-0"))
  (when-let [tiles (objects-named "tile-0")]
    (log "deleting tile-0, tiles=" tiles)
    (doall (map destroy-immediate tiles)))
  (tiles/init)
  (player/init)
  (anim/init))
;; indidual evals after everything is defined
(init)
(hook+ (object-named "player") :update :kbd #'animBoard.player/player-update)
