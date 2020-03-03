(ns cube-scene
  (:use
   arcadia.core
    arcadia.linear)
  (:import [UnityEngine Quaternion]))

; Instantiate(myPrefab, new Vector3(0, 0, 0), Quaternion.identity))
(defn init [])

; (UnityEngine/Instantiate "GameMaster" (v3 0 0 0) (Quaternion.identity))
; (instantiate "GameMaster" (v3 0 0 0) (Quaternion.identity))
; (instantiate "GameMaster" (v3 0 0 0) (Quaternion. 0 0 0 0))
; (instantiate "Entities/GameMaster" (v3 0 0 0) (qt))
(instantiate GameMaster (v3 0 0 0) (qt))
