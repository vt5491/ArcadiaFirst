(ns game.core
  (:use
   arcadia.core
    arcadia.linear
    arcadia.debug)
  (:require
   [game.frame :as frame]
   [game.base :as base])
  (:import [UnityEngine Quaternion Resources Input KeyCode Vector3
            Time Transform]))

(def ground (Resources/Load "ground"))
(def player (Resources/Load "player"))
; (def frame (Resources/Load "frame"))
(declare player-init-pos)

;; Note: before running this "reify" game.frame.clj and base.clj
(defn init []
  (when-let [grnd (object-named "ground")]
    (destroy-immediate grnd))
  (when (not (object-named "ground"))
    (def ground-go (instantiate ground))
    (set! (.-name ground-go) "ground"))
  (when-let [plyr (object-named "player")]
    (destroy-immediate player))
  (when (not (object-named "player"))
    (def player-go (instantiate player))
    (set! (.-name player-go) "player")
    ; (set! player-init-pos (.. player-go transform position))
    (alter-var-root #'player-init-pos (constantly (.. player-go transform position)))
    (log "player-init-pos=" player-init-pos))
  ;; frame
  (frame/init)
  (log "base.voxel-size=" base/voxel-size))

(init)

(defn log-name [obj role-key]
  (log (.name obj)))

(hook+
 (object-named "Cube")
 :update
 :log-name
 ; log-name
 player-update)

;; works: note: do *not* have the function parm
(hook-
 (object-named "Cube")
 :update
 :log-name)

(defn cube-rotate [go role-key]
  (let [pos (.position (.transform go))]
    (set! (.position (.transform go)) (v3 (+ (.x pos) 0.01) 0 0)))
  (let [rot (.rotation (.transform go))
        q (Quaternion. 0 10 0 0)]
    (.. go transform (Rotate 0 1.5 0))))

(hook+
 (object-named "Cube")
 :update
 :rotate
 cube-rotate)

(hook-
 (object-named "Cube")
 :update
 :rotate)

(hook+
 (object-named "Cube")
 :update
 :rotate
 #'game.core/cube-rotate)
;;

(defn rotate [obj role-key]
  (.. obj transform (Rotate 0 0.5 0)))

(hook+ (object-named "ground") :update :rotation #'game.core/rotate)
(hook- (object-named "ground") :update :rotation)

; (hook+ (object-named "Cube") :update :rotation #'game.core/cube-rotate)
(hook+ (object-named "Cube") :update :rotation #'game.core/rotate)
(hook- (object-named "Cube") :update :rotation)

;; player
(declare player-update)

; (def player-init-pos (v3 0 0 0))
; (def player-init-pos)
(def ^:dynamic player-init-pos)
; (def player-vx 0.1)
; (def player-vy 0.1)
; (def player-vz 0.1)
(def player-speed 3.0)

(defn player-init []
  (let [obj (object-named "player")]
    ; (set! (.position (.transform obj)) #'game.core/player-init-pos)
    ; (set! (.. obj transform position) #'game.core/player-init-pos)
    (set! (.. obj transform position) player-init-pos)))
    ; (alter-var-root (.. obj transform position) (constantly #'game.core/player-init-pos))))
    ; (.. obj transform (Rotate 0 0 0))))

; (player-init)

(defn player-update [obj role-key]
  ; (if (Input/GetKeyDown KeyCode/W)
  ;   (log "w key pressed"))
  (let [pos (.position (.transform obj))
        x (.x pos)
        y (.y pos)
        z (.z pos)]
    (cond
      (Input/GetKey KeyCode/A) (.. obj transform (Translate (v3* Vector3/left player-speed Time/deltaTime)))
      (Input/GetKey KeyCode/D) (.. obj transform (Translate (v3* Vector3/right player-speed Time/deltaTime)))
      (Input/GetKey KeyCode/W) (.. obj transform (Translate (v3* Vector3/forward player-speed Time/deltaTime)))
      (Input/GetKey KeyCode/S) (.. obj transform (Translate (v3* Vector3/back player-speed Time/deltaTime)))
      (Input/GetKey KeyCode/P) (.. obj transform (Translate (v3* Vector3/up player-speed Time/deltaTime)))
      (Input/GetKey KeyCode/N) (.. obj transform (Translate (v3* Vector3/down player-speed Time/deltaTime)))
      (Input/GetKey KeyCode/Q) (.. obj transform (Rotate 0 0.3 0))
      (Input/GetKey KeyCode/E) (.. obj transform (Rotate 0 -0.3 0)))))


(hook+ (object-named "player") :update :kbd #'game.core/player-update)
(hook- (object-named "player") :update :kbd)
