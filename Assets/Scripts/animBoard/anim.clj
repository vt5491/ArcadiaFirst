;; This ns is responsible for doing the board animations.
;; anim is refer to few, referred by many.
(ns animBoard.anim
  (:use
   arcadia.core
    arcadia.linear)
  (:require
   [animBoard.base :as base])
  (:import [UnityEngine Animator Resources Renderer]))

; (defrole anim-role
;   :state {:ybot-idle-scratch-foot (instantiate (Resources/Load "Animations/ybot@Idle_scratch_foot"))
;           :ybot-boxing (instantiate (Resources/Load "Animations/ybot@Boxing"))})

(defrole anim-role
  :state {})

(defn init []
  (when-let [anim (object-named "anim")]
    (destroy-immediate anim))
  ;; create an empty game object we can attach our state to
  (let [go (new UnityEngine.GameObject "anim")]
    (role+ go ::anim-role anim-role)
    (let [go (instantiate (Resources/Load "Animations/ybot@idle_scratch_foot"))
          anim (object-named "anim")]
      (set! (.-name go) "ybot@idle_scratch_foot")
      (child+ anim go)
      ; (UnityEngine.GameObject (AddComponent "Animator"))
      ; (GameObject/AddComponent "Animator")
      ; (.. go (AddComponent "Animator"))
      ; (.. go (AddComponent (type Animator)))
      (cmpt+ go Animator))
      ; (with-cmpt anim [animator Animator]
      ;   ; (set! (.. animator runtimeAnimatorController) ac)))
      ;   (set! (.. animator runtimeAnimatorController) (instantiate (Resources/Load "AnimationControllers/ybotAnimationController")))))
    (let [go (instantiate (Resources/Load "Animations/ybot@boxing"))
          anim (object-named "anim")]
      (set! (.-name go) "ybot@boxing")
      (set! (.. go transform position) (v3 1 0 0))
      (child+ anim go)))
      ; (with-cmpt anim [animator Animator]
      ;   (set! (.. animator runtimeAnimatorController) ac))))
  ;; timing thing. You have to add the AnimationController *after* initing the Animator component.
  (let [ac (instantiate (Resources/Load "AnimationControllers/ybotAnimationController"))]
    (log "ac=" ac)
    (set! (.-name ac) "ybotAnimationController")
    (let [go (object-named "ybot@idle_scratch_foot")]
      ; (log "go=" go)
      (with-cmpt go [animator Animator]
        ; (log "animator=" animator)
        ; (set! (.. animator runtimeAnimatorController) (instantiate (Resources/Load "AnimationControllers/ybotAnimationController")))
        (set! (.. animator runtimeAnimatorController) ac)))
        ; (log "animator Controller=" (.. animator runtimeAnimatorController))))
    (let [go (object-named "ybot@boxing")]
      ; (log "go=" go)
      (with-cmpt go [animator Animator]
        ; (log "animator=" animator)
        ; (set! (.. animator runtimeAnimatorController) (instantiate (Resources/Load "AnimationControllers/ybotAnimationController")))
        (set! (.. animator runtimeAnimatorController) ac)))))
        ; (log "animator Controller=" (.. animator runtimeAnimatorController))))))
    ; (update-state go ::anim-role
    ;               (assoc go ::anim-role :ybot-idle-scratch-foot (instantiate (Resources/Load "Animations/ybot@Idle_scratch_foot"))))
    ; (update-state go ::anim-role
    ;               {:ybot-idle-scratch-foot (instantiate (Resources/Load "Animations/ybot@Idle_scratch_foot"))})
    ;; works
    ; (update-state go ::anim-role (fn [x] (assoc (state go ::anim-role)
    ;                                            :ybot-idle-scratch-foot (instantiate (Resources/Load "Animations/ybot@Idle_scratch_foot")))))))
    ; (update-state go ::anim-role (assoc (state go ::anim-role)
    ;                                     :ybot-idle-scratch-foot (instantiate (Resources/Load "Animations/ybot@Idle_scratch_foot"))))))

(defn anim-key-handler []
  (cond
    (Input/GetKeyDown KeyCode/Keypad1)
    (do
      (log "key1 pressed")
      (let [go (object-named "ybot@idle_scratch_foot")]
        (with-cmpt go [anim Animator]
          (.. anim (Play "IdleFootScratch")))))
    (Input/GetKeyDown KeyCode/Keypad2)
    (do
      (log "key2 pressed")
      (let [go (object-named "ybot@boxing")]
        (with-cmpt go [anim Animator]
          (.. anim (Play "Boxing")))))))

;; optional manual executions
(init)

(in-ns 'animBoard.anim)
