(ns game.snake
  (:use
   arcadia.core
    arcadia.linear)
  (:require
   [game.base :as base])
  (:import [UnityEngine Quaternion Resources Input KeyCode Vector3
            Time Transform BoxCollider GameObject Rigidbody]))

; (def body-cells [])
; (def ^:dynamic last-tick Time/time)
(def snake-head-pos (v3 0 4 0))

(defn set-cell-pos [cell pos]
  (with-cmpt cell [tr Transform]
    (set! (.. tr position) pos)))

(defn init-snake-head []
  (let [cell (object-named "cell-0")]
    (with-cmpt cell [tr Transform]
      ; (set! (. tr position) (v3 0 4 0))
      (set! (. tr position) snake-head-pos))
    (update-state cell :snake-vel (fn [state]
                                    (assoc state :vx 0, :vy base/voxel-size, :vz 0)))))
(init-snake-head)

(defn init-snake-body []
  (let [snake (object-named "snake")]
    (for [i [1 2 3]]
      (do
        (let [obj (object-named (str "cell-" i))
              name (str "cell-" i)]
            (when obj (destroy-immediate obj)))
        (let [cell (instantiate (Resources/Load "cell"))
              name (str "cell-" i)]
          (set! (.name cell) name)
          (child+ snake cell)
          (with-cmpt cell [tr Transform]
            (set! (.. tr position) (v3+ snake-head-pos (v3 0 (* base/voxel-size i) 0)))))))))

(init-snake-body)

(defn init-snake []
  (init-snake-head)
  (init-snake-body))

(init-snake)

(defn init []
  (when-let [obj (object-named "snake")]
    (destroy-immediate obj))
  (let [obj (GameObject.)]
    (set! (.name obj) "snake")
    (state+ obj :snake-tick {:last-tick (Time/time)}))
  (let [snake (object-named "snake")
        cell (create-primitive :cube)]
    (child+ snake cell)
    (set! (.name cell) "cell-0")
    ; (.add cell (Rigidbody.))
    (.AddComponent cell Rigidbody)
    (state+ cell :snake-vel {:vx 0, :vy base/voxel-size, :vz 0})
    (with-cmpt cell [rb Rigidbody]
      (set! (.. rb useGravity) false))
    (with-cmpt cell [tr Transform]
      ; (set! (. tr position) (v3 0 4 0))
      (init-snake-pos)
      (set! (. tr localScale) (v3 base/voxel-size base/voxel-size base/voxel-size)))
    (with-cmpt cell [bc BoxCollider]
      (set! (.. bc size) (v3 base/voxel-size base/voxel-size base/voxel-size))
      (set! (.. bc isTrigger) true)))
  (init-snake))

(init)

;; transer state (i.e. pos) "down" the snake body.
(defn pump-cell-states []
  (let [snake (object-named "snake")]))

(defn body-update []
  (let [snake (object-named "snake")]
    (let [r (map-indexed
             #(when (< %1 (- (count (children snake)) 1))
                (let [cells (reverse (children snake))
                      curr-cell %2
                      next-cell (nth cells (+ %1 1))]
                  (log "current coords for cell %1="
                       %1 ",curr-cell=" curr-cell "," (.. curr-cell transform position)
                       ", next-cell=" next-cell "," (.. next-cell transform position))
                  (set! (.. curr-cell transform position) (.. next-cell transform position))))
             (reverse (children snake)))]
      (doall r))))
      ; r)))


(defn snake-update [obj role-key]
  (let [snake-state (state obj role-key)
        last-tick (:last-tick snake-state)]
    (when (> (- (Time/time) last-tick) 0.8)
       (body-update)
       ;; now move head to new pos
       (log "tick quanta triggered")
       (let [cell-0 (first (children obj))
             ; cell-1 (nth (children obj) 1)
             cell-state (state cell-0 :snake-vel)]
         (log "cell-state vx=" (:vx cell-state) ", vy=" (:vy cell-state))
         (with-cmpt cell-0 [tr Transform]
           (let [pos (. tr position)]
             ; (set! (.. cell-1 transform position) (.. tr position))
             (set! (. tr position)
               (v3 (- (.x pos) (:vx cell-state)) (- (.y pos) (:vy cell-state)) (- (.z pos) (:vz cell-state)))))))
       ; (update-state obj, role-key, f & args)
       ; (with)
       (update-state obj role-key (fn [state]
                                    ; (log "unknown-dummy=" unknown-dummy)
                                    ; (assoc (state obj role-key) :last-tick (Time/time))
                                    (assoc state :last-tick (Time/time)))))))
      ; (alter-var-root last-tick (Time/time)))))

;; Note: simply specifying the function name triggers "attempting to serialize something that is not serializable" errors
; (hook+ (object-named "snake") :update :snake-tick snake-update)
(hook+ (object-named "snake") :update :snake-tick #'snake-update)
; (hook- (object-named "snake") :update :snake-tick)

; (defn snake-collision-handler [obj role-key collision]
;   (log "snake collision detected, collision=" collision))
        ;; and start moving horizontally

; (defn snake-trigger-handler [obj role-key collider])
(defn cell-trigger-enter [cell role-key collider]
  (log "cell trigger detected, collider=" collider ",role-key=" role-key)
  (let [snake-vel-state (state cell :snake-vel)
        vx (:vx snake-vel-state)
        vy (:vy snake-vel-state)
        vz (:vz snake-vel-state)]
    (when vy
      ;; move the cell up one unit and to left so it's on top of the collision block
      (with-cmpt cell [tr Transform]
        (let [pos (.. tr position)]
          (set! (.. tr position) (v3 (- (.x pos) base/voxel-size) (+ (.y pos) (* base/voxel-size 1)) (.z pos))))))))
      ;; and start moving horizontally
      ; (update-state cell :snake-vel
      ;               (fn [state]
      ;                 (log "cell-trigger-enter: updating vx")
      ;                 (assoc state :vx base/voxel-size, :vy 0, :vz 0))))))


; (hook+ (object-named "snake") :on-collision-enter :snake-collision #'snake-collision-handler)
; (hook- (object-named "snake") :on-collision-enter :snake-collision)
; (hook+ (object-named "cell-0") :on-collision-enter :snake-collision #'snake-collision-handler)
(hook+ (object-named "cell-0") :on-trigger-enter :snake-collision #'cell-trigger-enter)

(defn cell-trigger-exit [obj role-key collider]
  (log "cell-trigger-exit: collider=" collider)
  (let [snake-vel-state (state obj :snake-vel)
        vx (:vx snake-vel-state)
        vy (:vy snake-vel-state)
        vz (:vz snake-vel-state)]
    (when vx (update-state obj :snake-vel (fn [state]
                                            (log "cell-trigger-exit: updating vy")
                                            (assoc state :vx 0, :vy base/voxel-size, :vz 0))))))

(hook+ (object-named "cell-0") :on-trigger-exit :snake-collision #'cell-trigger-exit)
; (hook- (object-named "cell-0") :on-trigger-exit :snake-collision)
