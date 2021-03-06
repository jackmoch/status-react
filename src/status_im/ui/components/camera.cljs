(ns status-im.ui.components.camera
  (:require [goog.object :as object]
            [reagent.core :as reagent]
            [clojure.walk :as walk]
            [status-im.react-native.js-dependencies :as js-dependecies]))

(def default-camera (.-default js-dependecies/camera))

(defn constants [t]
  (-> (object/get js-dependecies/camera "constants" t)
      (js->clj)
      (walk/keywordize-keys)))

(def aspects (constants "Aspect"))
(def capture-targets (constants "CaptureTarget"))
(def torch-modes (constants "TorchMode"))

(defn set-torch [state]
  (set! (.-torchMode default-camera) (get torch-modes state)))

(defn request-access-ios [then else]
  (-> (.checkVideoAuthorizationStatus default-camera)
      (.then then)
      (.catch else)))

(defn camera [props]
  (reagent/create-element default-camera (clj->js (merge {:inverted true} props))))

(defn get-qr-code-data [code]
  (.-data code))
