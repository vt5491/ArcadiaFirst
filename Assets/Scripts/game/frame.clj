(ns game.frame
  (:use
   arcadia.core
    arcadia.linear)
  (:import [UnityEngine Quaternion Resources Input KeyCode Vector3
            Time Transform]))

(def width 10)
(def height 10)
(def pixel-unit 0.1)
; (def frame (Resources/Load "frame"))
      ; (let [left-scale (.. left-edge transform localScale)]
      ;   (with-cmpt left-edge [tr Transform]
      ;     (set! (.. tr localScale) (v3 (.x left-scale) (.y left-scale) (* (.z left-scale) 4))))))

(defn init []
  (when-let [frm (object-named "frame")]
    (destroy-immediate frm))
  (let [obj (instantiate (Resources/Load "frame"))]
    (set! (.-name obj) "frame"))
  (let [left-edge (object-named "left-edge")
        right-edge (instantiate left-edge)
        bottom-edge (instantiate left-edge)
        block (instantiate left-edge)]
    (set! (.-name right-edge) "right-edge")
    (child+ (object-named "frame") right-edge)
    (set! (.-name bottom-edge) "bottom-edge")
    (child+ (object-named "frame") bottom-edge)
    (set! (.-name block) "block")
    (child+ (object-named "frame") block)
    (let [left-pos (.. left-edge transform position)
          left-scale (.. left-edge transform localScale)]
      (with-cmpt left-edge [tr Transform]
        (set! (.. tr localScale) (v3 (.x left-scale) (.y left-scale) (* (.z left-scale) 4))))
      (with-cmpt right-edge [tr Transform]
        ; (set! (. tr position) (v3 3 2 0))
        ; (let [left-pos (.. left-edge transform position)]
        (set! (. tr position) (v3 (* (.x left-pos) -1) (.y left-pos) (.z left-pos)))
        (set! (.. tr localScale) (v3 (.x left-scale) (.y left-scale) (* (.z left-scale) 4))))
      (with-cmpt bottom-edge [tr Transform]
        (.. tr (Rotate 0 90 0))
        (set! (. tr position) (v3 0 0 (.z left-pos)))
        (set! (.. tr localScale) (v3 (.x left-scale) (.y left-scale) (* (.z left-scale) 6))))
      (with-cmpt block [tr Transform]
        (.. tr (Rotate 0 90 0))
        (set! (. tr position) (v3 0 2 (.z left-pos)))
        (set! (.. tr localScale) (v3 (.x left-scale) (.y left-scale) (* (.z left-scale) 1.0)))))))
         ; (set! (. tr position) (v3 (* (.x left-pos) -1) (.y left-pos) (.z left-pos))))))))
         ; (let))))))


(init)
