
(def tmp-c (create-primitive :cube))


(for [i [1 2 3]]
  (log i))

(let [snake (object-named "snake")]
  (for [i [1 2 3]]
    (let [cell (create-primitive :cube)]
      (set! (.name cell) (str "cell-" i))
      (child+ snake cell))))

(let [snake (object-named "snake")]
  (for [i [1 2 3]]
    (let [cell (instantiate (Resources/Load "cell"))]
      (set! (.name cell (str "cell-" i)))
      (with-cmpt cell [tr Transform]
        (set! (..))))))

(nth [0 1 2] 0)

(for [x (range 3 7)]
  (* x x))

(.transform (object-named "cell-1"))

;; returns [0, cell-0], [1, cell-1].. [3, cell-3]
(let [snake (object-named "snake")]
  (let [r (map-indexed #(str "arg1=" %1 ",arg2=" %2) (children snake))]
    r))

(let [snake (object-named "snake")]
  (let [r (map-indexed #(str "arg1=" %1 ",arg2=" %2) (reverse (children snake)))]
    r))


(reverse [0 1 2])

(let [snake (object-named "snake")]
  (let [r (map-indexed #(when (> %1 0) (set!)) (children snake))]
    r))

(let [snake (object-named "snake")]
  (let [r (map-indexed
           #(
             (let [cells (children snake)]
               (when (> %1 0)
                 (let [
                       curr-cell (nth cells %1)
                       prior-cell (nth cells (= %1 1))]
                   (log "current coords for cell %1=" %1 ",curr-cell=" curr-cell ", prior-cell=" prior-cell)))))
           r)]
    r))
(let [snake (object-named "snake")]
  (let [r (map-indexed
           #(when (> %1 0)
              (let [cells (children snake)
                    curr-cell %2
                    prior-cell (nth cells (- %1 1))]
                (log "current coords for cell %1="
                     %1 ",curr-cell=" (.. curr-cell transform position)
                     ", prior-cell=" (.. prior-cell transform position))))
                ; (set! (.. curr-cell transform position) (.. prior-cell transform position))))
           (children snake))]
    r))

(count (children (object-named "snake")))

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
    (doall r)))
