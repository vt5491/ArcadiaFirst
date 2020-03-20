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

;; return the pos. for a given index. Note: this is independent of x and y axis
;; i.e. the user needs to interpret the result as either a x or y position.
(defn idx-pos [idx]
  (let [x-adj (* (/ (- base/board-width 1) 2) tile-width)]
    (+ (* (mod idx base/board-width) (- (+ tile-width tile-gap))) x-adj)))

;; set the x-pos for a tile of a given index
(defn set-x-pos [tile idx])

(defn init []
  (log "tiles.init2: base/board-width=" base/board-width)
  ; (when-let [tile-array (objects-named #"tile-\d+")]
  ;   ; (log "deleting " (count tiles) " old tiles=")
  ;   (doall (map destroy-immediate tile-array)))
  ; (.. UnityEngine (GameObject "tiles"))
  ; (GameObject "tiles")
  ; (UnityEngine/GameObject "tiles")
  ; (UnityEngine/instantiate "tiles")
  ; (instantiate "tiles")
  ; (GameObject/Instantiate "tiles")
  ; (GameObject/Instantiate)
  ; (let [tiles (gobj (new UnityEngine.GameObject "tiles"))])
  (when-let [tiles (object-named "tiles")]
    (destroy-immediate tiles))
  (let [tiles (new UnityEngine.GameObject "tiles")]
    (for [i (range 0 (* base/board-width base/board-width))]
      (do
        (log "tiles.init: i=" i)
        (let [tile (create-primitive :cube)]
          ; x-adj (* (/ (- base/board-width 1) 2) tile-width)]
          (set! (.name tile) (str "tile-" i))
          (child+ tiles tile)
          (with-cmpt tile [tr Transform]
            (set! (.. tr localScale) (v3 tile-width tile-thickness tile-width))
            (set! (.. tr position) (v3 (idx-pos i) 0 (idx-pos (int (/ i base/board-width)))))))))))

;; optional manual evals
(init)
