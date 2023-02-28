(ns com.latacora.signcryption-demo
  (:require
   #_[caesium.crypto.aead :as aead]
   #_[caesium.crypto.sign :as sign]
   [caesium.util :as cutil]))

(def version "N1")

;; # Topic keys

(defn symmetric-test-key
  [topic n]
  (let [topic-bytes (-> topic name .getBytes)
        key-bytes (concat topic-bytes (repeat n))]
    (->> key-bytes (take 32) byte-array)))

(comment
  (seq (symmetric-test-key :a 1))
  #_(97 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)

  (seq (symmetric-test-key :b 2))
  #_(98 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2))

(def topic-a-key-1 (symmetric-test-key \a 1))
(def topic-a-key-2 (symmetric-test-key \a 2))
(def topic-b-key-1 (symmetric-test-key \b 1))
(def topic-b-key-2 (symmetric-test-key \b 2))

;; # Signing identities

;; TKTK
