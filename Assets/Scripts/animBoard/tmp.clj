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
    (.. anim (Play "IdleQuiet"))))
    ; (log "tr=" tr)
    ; (log "tr.ToString=" (-> tr ToString))
    ; (log "tr.ToString=" (.. tr (ToString)))))
    ; (log "toSTring=" (Transform/ToString anim))))
    ; (Animator/Play "IdleQuiet")))
