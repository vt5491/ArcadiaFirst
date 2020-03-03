(ns dummy
  (:use
   arcadia.core
    arcadia.linear)
  (:require
   [game.core :as core])
  (:import [UnityEngine Resources]))
; (+ 1 2)

(def cube (create-primitive :cube))

(defcomponent AbcComp [state]
  (log "hello there"))
  ; (Start [this] (set! state {:position (Vector3. 0 2 0)})))
(cmpt+ cube UnityEngine.Component)

(def pos (v3 1 1 1))

(log "hello")

(def c (object-named "Cube"))

(log (.. c transform position))
(log (-> c transform position))
((log (.. c (.-transform) (.-position))))
(set! (.. c transform position) (v3 0 0 0))

(log "pos2=" (-> c (.-transform) (.-position)))
;; this is how instantiate a resource (similiar to instantiating a prefab1)
; rend.material.mainTexture = Resources.Load("glass") as Texture;

(def cr (Resources/Load "Cube"))

(log cr)

;; it will pop into the running scene after the following
(instantiate cr)
;; end Resources example
(core.c)
(log)
