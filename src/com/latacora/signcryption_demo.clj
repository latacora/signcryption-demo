(ns com.latacora.signcryption-demo
  (:require
   [caesium.crypto.aead :as aead]
   [caesium.crypto.sign :as sign]
   [caesium.]
   [caesium.util :as cutil]
   [caesium.byte-bufs :as cbb]))

(comment
  ;; Add Homebrew to the library search path for development on macOS

  ;; Note: this isn't guaranteed to work according to the JLS but it works on
  ;; every JVM I've tried on modern macOS + libsodium from Homebrew.
  (let [lib-path "java.library.path"
        cur-path (System/getProperty lib-path)
        homebrew-path "/opt/homebrew/lib"]
    ;; You're really supposed to use the correct separator here but because it
    ;; only matters on macOS we know what that separator is anyway.
    (when-not (str/includes? cur-path homebrew-path)
      (System/setProperty lib-path (str cur-path ":" homebrew-path)))))

(def version "N1")

;; # Symmetric encryption

;; ## Test key generation

(defn symmetric-test-key
  [topic n]
  (let [topic-bytes (-> topic name .getBytes)
        key-bytes (concat topic-bytes (repeat n))]
    (->> key-bytes (take 32) byte-array)))

(def topic-a-key-1 (symmetric-test-key \a 1))
(def topic-a-key-2 (symmetric-test-key \a 2))
(def topic-b-key-1 (symmetric-test-key \b 1))
(def topic-b-key-2 (symmetric-test-key \b 2))

(comment
  (cutil/hexify (symmetric-test-key :a 1))
  "6101010101010101010101010101010101010101010101010101010101010101"

  (cutil/hexify (symmetric-test-key :b 2))
  "6202020202020202020202020202020202020202020202020202020202020202")

;; # Signing

;; ## Serialization utilities

(defn serialize-signing-key
  [key-pair]
  (let [{:keys [public secret]} key-pair]
    (map cutil/hexify [public secret])))

(defn deserialize-signing-key
  [serialized]
  (->> serialized (map cutil/unhexify) (zipmap [:oublic :secret])))

;; ## Producer identities

(def producer-1-keypair (sign/keypair! "producer-1"))
(def producer-2-keypair (sign/keypair! "producer-2"))

;; TKTK

(defn send
  [])
