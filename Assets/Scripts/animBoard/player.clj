(ns animBoard.player
  (:use
   arcadia.core
    arcadia.linear)
  (:require
   [animBoard.base :as base]
   [animBoard.tiles :as tiles])
  (:import [UnityEngine Animator Material Quaternion Resources Input KeyCode Vector3
            Shader Time Transform MeshRenderer]))

; (def player-prefab (Resources/Load "player"))
(declare player-update)
(declare player-role)

; (def player-init-pos (v3 0 0 0))
; (def player-init-pos)
(def ^:dynamic player-init-pos)
; (def player-vx 0.1)
; (def player-vy 0.1)
; (def player-vz 0.1)
; (def player-speed 3.0)

(defn abc []
  (log "player.abc: entered"))
; (defrole player-role
;   :start {:speed 3.0}
;   :update #'animBoard.player/player-update)

(defn init []
  (when-let [players (objects-named "player")]
    ; (doall (map retire players))
    (doall (map destroy-immediate players)))
  ; (when-let [go (object-named "player")]
  ;   ; (destroy-immediate plyr)
  ;   (retire go))
  ; (let [obj (object-named "player")])
  ; (let [tmp (instantiate (Resources/Load "player"))])
  (let [go (instantiate (Resources/Load "player"))]
    (set! (.-name go) "player"))
  (let [ obj (object-named "player")]
    (role+ obj ::player-role player-role)
    ; (set! (.position (.transform obj)) #'game.core/player-init-pos)
    ; (set! (.. obj transform position) #'game.core/player-init-pos)
    ; (set! (.. obj transform position) player-init-pos)
    (set! (.. obj transform position) (v3 0 0 0))
    (let [cam (instantiate (Resources/Load "camera"))]
      (set! (.-name cam) "camera")
      (child+ obj cam))))
    ; (child+ obj (instantiate (Resources/Load "camera")))))
    ; (alter-var-root (.. obj transform position) (constantly #'game.core/player-init-pos))))
    ; (.. obj transform (Rotate 0 0 0))))

; (player-init)

(defn player-update [obj role-key]
  (let [pos (.position (.transform obj))
        speed (:speed (state obj role-key))]
    (cond
      (Input/GetKey KeyCode/A) (.. obj transform (Translate (v3* Vector3/left speed Time/deltaTime)))
      (Input/GetKey KeyCode/D) (.. obj transform (Translate (v3* Vector3/right speed Time/deltaTime)))
      (Input/GetKey KeyCode/W) (.. obj transform (Translate (v3* Vector3/forward speed Time/deltaTime)))
      (Input/GetKey KeyCode/S) (.. obj transform (Translate (v3* Vector3/back speed Time/deltaTime)))
      (Input/GetKey KeyCode/P) (.. obj transform (Translate (v3* Vector3/up speed Time/deltaTime)))
      (Input/GetKey KeyCode/N) (.. obj transform (Translate (v3* Vector3/down speed Time/deltaTime)))
      (Input/GetKey KeyCode/Q) (.. obj transform (Rotate 0 0.3 0))
      (Input/GetKey KeyCode/E) (.. obj transform (Rotate 0 -0.3 0))
      ; Object.GetComponent<MeshRenderer> ().material = Material1;
      ; creature-material (Material. (Shader/Find "Specular"))
      ; Material yourMaterial = (Material)Resources.Load("MaterialName", typeof(Material));
      (or (Input/GetKeyDown KeyCode/Keypad1) (Input/GetKeyDown KeyCode/Keypad2))
      (do
        (let [go (object-named "ybot@Idle_scratch_foot")]
          (with-cmpt go [anim Animator]
            (cond
              (Input/GetKeyDown KeyCode/Keypad1)
              (do
                (log "1 key pressed")
                (.. anim (Play "Idle"))
                (let [go (object-named "tile-0")
                      red-mat (Resources/Load "RedMat")]
                  ; (.. go (SendMessage "ToggleMat"))
                  ; (.. go (SendMessage "toggle-mat"))
                  ; (.. obj (SendMessage "abc"))
                  (tiles/toggle-mat)
                  (with-cmpt go [rend MeshRenderer]
                    (set! (.. rend material) red-mat))))
              (Input/GetKeyDown KeyCode/Keypad2)
              (do
                (log "2 key pressed")
                (.. anim (Play "Boxing"))))))))))

; (hook+ (object-named "player") :update :kbd player-update)
;;
;; optional post eval stuff (eval the prior code and do this after a "player" is defined in the runtim env.)
;;
(init)

; (if (object-named "player")
;   (hook+ (object-named "player") :update :kbd #'animBoard.player/player-update)
;   ; (hook+ (object-named "player") :update :kbd player-update)
;   (log "player object not found in runtime"))
;
; (hook- (object-named "player") :update :kbd)
;; use roles to setup hooks
(defrole player-role
  :state {:speed 3.0}
  :update #'animBoard.player/player-update)
  ; :toggleMat #'animBoard.tiles/toggle-mat)
