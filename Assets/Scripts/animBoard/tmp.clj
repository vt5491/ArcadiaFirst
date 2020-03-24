; (child+ x child)

(child+ (object-named "player") ())

(.instantiate UnityEngine.Camera)
(.. UnityEngine.Camera instantiate)
(Resources/Load "camera")

(instantiate (Resources/Load "camera"))

; ybot@Idle_scratch_foot
(let [go (object-named "ybot@Idle_scratch_foot")]
  (with-cmpt go [anim Animator
                 tr Transform]
    ; (log "anim=" anim ", anim.isPlaying=" (.. anim isPlaying))
    ; (log "anim=" anim ", anim.isPlaying=" (isPlaying anim))
    ; (log "anim=" anim ", anim.currentStateInfo=" (.GetCurrentAnimatorStateInfo anim))
    (log "anim=" anim ", anim.currentStateInfo=" (.. anim (GetCurrentAnimatorStateInfo 0)))
    (log "anim=" anim ", anim.isHuman=" (.. anim isHuman))
    ; (.. anim (Play "IdleQuiet"))))
    (.. anim (Play "Boxing"))))
    ; (log "tr=" tr)
    ; (log "tr.ToString=" (-> tr ToString))
    ; (log "tr.ToString=" (.. tr (ToString)))))
    ; (log "toSTring=" (Transform/ToString anim))))
    ; (Animator/Play "IdleQuiet")))

(def abc { :a 1 :b 2})

(update-state go ::anim-role (assoc (state (object-named "anim") ::anim-role)
                                    :ybot-idle-scratch-foot
                                    (instantiate (Resources/Load "Animations/ybot@Idle_scratch_foot"))))

(state (object-named "anim") ::anim-role)

(update-state (object-named "anim") ::anim-role {:a 1})

(update-state (object-named "anim") ::anim-role (fn [x] {:a 1}))
(update-state (object-named "anim") ::anim-role (fn [x] {:b 2}))
(update-state (object-named "anim") ::anim-role (fn [x] (assoc (state (object-named "anim")) :c 3)))

(update-state (object-named "anim") ::anim-role (fn [x] (assoc (state (object-named "anim") ::anim-role) :c 3)))

(destroy-immediate (o))

(let [go (instantiate (Resources/Load "Animations/ybot@Idle_scratch_foot"))
      anim (object-named "anim")]
  (set! (.-name go) "ybot@Idle_scratch_foot")
  (child+ anim go))

(let [anim (object-named "anim")
      ; cmpts (.. anim (GetComponentsInChildren (type UnityEngine.Transform)))
      cmpts (.. anim (GetComponentsInChildren UnityEngine.Transform))]
  ; (log "cmpts=" (count cmpts)))
  (log "child-cnt" (count (children anim)))
  (log "children"  (children anim))
  (log "type="  (type (children anim)))
  (log "first child=" (nth (children anim) 0))
  (log "first child name=" (.name (nth (children anim) 0))))
  ; (log "name=" (.name (first children))))
abc
(type [ 1 2 3])
(nth [1 2 3] 0)
(type UnityEngine.GameObject)
(type UnityEngine.Transform)
; GetComponent()<Renderer>.enabled = true/false;
(let [go (object-named "ybot@Idle_scratch_foot")]
  (.. go (SetActive true)))

(let [go (object-named "ybot@Idle_scratch_foot")]
  (set! (.. go enabled) false))
  ; (with-cmpt go [rend Renderer]
  ;   (log "rend=" rend)))
    ; (set! (.. rend enabled) false)))

(object-named "ybot@Idle_scratch_foot")

(Resources/FindObjectsOfTypeAll)

(let [go (object-named "ybot@idle_scratch_foot")]
  (log "go=" go)
  (with-cmpt go [animator Animator]
    (log "animator=" animator)
    (set! (.. animator runtimeAnimatorController) (instantiate (Resources/Load "AnimationControllers/ybotAnimationController")))
    (log "animator Controller=" (.. animator runtimeAnimatorController))))
