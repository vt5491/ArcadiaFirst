(ns animBoard.tiles
  (:use
   arcadia.core
    arcadia.linear)
  (:require
   [animBoard.base :as base])
  (:import [UnityEngine Quaternion Resources Input KeyCode Vector3
            Time Transform]))

(def tile-width 1)
(def tile-thickness 0.1)
(def tile-gap 0.1)

; (defn init []
;   (log "base/board-size=" base/board-size)
;   (when-let [tiles (objects-named "tile-0")]
;     (log "deleting tile-0, tiles=" tiles)
;     (doall (map destroy-immediate tiles)))
;   (let [tile (create-primitive :cube)]
;     (log "created tile-0")
;     (set! (.name tile) (str "tile-0"))
;     (with-cmpt tile [tr Transform]
;       (set! (.. tr localScale) (v3 1.0 0.1 1.0))
;       (log "tile-0 scale=" (.. tr localScale)))))

; (defn init []
;   (when-let [tiles (objects-named #"tile-\d+")]
;     (log "deleting " (count tiles) " old tiles=")
;     (doall (map destroy-immediate tiles)))
;   (for [i (range 0 base/board-width)]
;     (do
;       (let [tile (create-primitive :cube)
;             x-adj (* (/ (- base/board-width 1) 2) tile-width)]
;         (set! (.name tile) (str "tile-" i))
;         (with-cmpt tile [tr Transform]
;           (set! (.. tr localScale) (v3 tile-width tile-thickness tile-width))
;           ; (set! (.. tr position) (v3 (+ (* i (- tile-width)) x-adj) 0 0))
;           ; (set! (.. tr position) (v3 (+ (* i (* (+ tile-width tile-gap) -1)) x-adj) 0 0))
;           (set! (.. tr position) (v3 (+ (* i (- (+ tile-width tile-gap))) x-adj) 0 0)))))))

;; return the pos. for a given index. Note: this is independent of x and y axis
;; i.e. the user needs to interpret the result as either a x or y position.
(defn idx-pos [idx]
  (let [x-adj (* (/ (- base/board-width 1) 2) tile-width)]
    (+ (* (mod idx base/board-width) (- (+ tile-width tile-gap))) x-adj)))

;; set the x-pos for a tile of a given index
(defn set-x-pos [tile idx])

(defn init []
  (when-let [tiles (objects-named #"tile-\d+")]
    ; (log "deleting " (count tiles) " old tiles=")
    (doall (map destroy-immediate tiles)))
  (for [i (range 0 (* base/board-width base/board-width))]
    (do
      (let [tile (create-primitive :cube)]
            ; x-adj (* (/ (- base/board-width 1) 2) tile-width)]
        (set! (.name tile) (str "tile-" i))
        (with-cmpt tile [tr Transform]
          (set! (.. tr localScale) (v3 tile-width tile-thickness tile-width))
          ; (set! (.. tr position) (v3 (+ (* i (- tile-width)) x-adj) 0 0))
          ; (set! (.. tr position) (v3 (+ (* i (* (+ tile-width tile-gap) -1)) x-adj) 0 0))
          ; (set! (.. tr position) (v3 (+ (* i (- (+ tile-width tile-gap))) x-adj) 0 0))
          ; (set! (.. tr position) (v3 (idx-pos i) 0 (mod (idx-pos (float (/ i base/board-width))) base/board-width)))
          ; (set! (.. tr position) (v3 (idx-pos i) 0 (if (< i base/board-width) (idx-pos 0) (idx-pos 1))))
          (set! (.. tr position) (v3 (idx-pos i) 0 (idx-pos (int (/ i base/board-width))))))))))

(init)
