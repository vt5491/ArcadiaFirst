;; player is refer to many, referred by few.
(ns animBoard.player
  (:use
   arcadia.core
    arcadia.linear)
  (:require
   [animBoard.base :as base]
   [animBoard.tiles :as tiles]
   [animBoard.anim :as anim])
  (:import [UnityEngine Animator Material Quaternion Resources Input KeyCode Vector3
            Shader Time Transform MeshRenderer]))

(declare player-update)
(declare player-role)

(def ^:dynamic player-init-pos)

;; use roles to setup hooks
(defrole player-role
  :state {:speed 3.0}
  :update #'animBoard.player/player-update)

(defn init []
  (when-let [players (objects-named "player")]
    (doall (map destroy-immediate players)))
  (let [go (instantiate (Resources/Load "player"))]
    (set! (.-name go) "player"))
  (let [ obj (object-named "player")]
    (role+ obj ::player-role player-role)
    (set! (.. obj transform position) (v3 0 0 0))
    (let [cam (instantiate (Resources/Load "camera"))]
      (set! (.-name cam) "camera")
      (child+ obj cam))))

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
      (or (Input/GetKeyDown KeyCode/Keypad1) (Input/GetKeyDown KeyCode/Keypad2))
      (animBoard.anim/anim-key-handler))))
      ; Object.GetComponent<MeshRenderer> ().material = Material1;
      ; creature-material (Material. (Shader/Find "Specular"))
      ; Material yourMaterial = (Material)Resources.Load("MaterialName", typeof(Material));
      ; (or (Input/GetKeyDown KeyCode/Keypad1) (Input/GetKeyDown KeyCode/Keypad2))
      ; (do
      ;   (let [go (object-named "ybot@Idle_scratch_foot")]
      ;     (with-cmpt go [anim Animator]
      ;       (cond
      ;         (Input/GetKeyDown KeyCode/Keypad1)
      ;         (do
      ;           (log "1 key pressed")
      ;           (.. anim (Play "Idle"))
      ;           (let [go (object-named "tile-0")
      ;                 red-mat (Resources/Load "RedMat")]
      ;             ; (.. go (SendMessage "ToggleMat"))
      ;             ; (.. go (SendMessage "toggle-mat"))
      ;             ; (.. obj (SendMessage "abc"))
      ;             (tiles/toggle-mat)
      ;             (with-cmpt go [rend MeshRenderer]
      ;               (set! (.. rend material) red-mat))))
      ;         (Input/GetKeyDown KeyCode/Keypad2)
      ;         (do
      ;           (log "2 key pressed")
      ;           (.. anim (Play "Boxing"))))))))))

; (hook+ (object-named "player") :update :kbd player-update)
;;
;; optional post eval stuff (eval the prior code and do this after a "player" is defined in the runtim env.)
;;
(init)

  ; :toggleMat #'animBoard.tiles/toggle-mat)
