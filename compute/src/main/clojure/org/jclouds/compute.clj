;
;
; Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
;
; ====================================================================
; Licensed under the Apache License, Version 2.0 (the "License");
; you may not use this file except in compliance with the License.
; You may obtain a copy of the License at
;
; http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.
; ====================================================================
;

(ns org.jclouds.compute
  "A clojure binding to the jclouds ComputeService.

Current supported providers are:
   [ec2, aws-ec2, eucualyptus-partnercloud-ec2, cloudservers, 
    cloudservers-uk, cloudservers-us, byon,
    trmk-ecloud, trmk-vcloudexpress, vcloud, bluelock, eucalyptus, 
    slicehost, elastichosts-lon-p, elastichosts-sat-p, elastichosts, 
    openhosting-east1, serverlove-z1-man, skalicloud-sdg-my,
    elastichosts-lon-b, cloudsigma-zrh, vcloudexpress, stub]

Here's an example of getting some compute configuration from rackspace:

  (use 'org.jclouds.compute)
  (use 'clojure.contrib.pprint)

  (def provider \"cloudservers\")
  (def provider-identity \"username\")
  (def provider-credential \"password\")

  ;; create a compute service
  (def compute
    (compute-service provider provider-identity provider-credential))

  (with-compute-service [compute]
    (pprint (locations))
    (pprint (images))
    (pprint (nodes))
    (pprint (hardware-profiles)))

Here's an example of creating and running a small linux node with the tag
webserver:

  ;; create a compute service using ssh and log4j extensions
  (def compute
    (compute-service
      provider provider-identity provider-credential :ssh :log4j))

  (run-node \"webserver\" compute)

See http://code.google.com/p/jclouds for details."
  (:use org.jclouds.core
        (clojure.contrib logging core))
  (:require
   [clojure.contrib.condition :as condition])
  (:import java.io.File
           java.util.Properties
           [org.jclouds.domain Location]
           [org.jclouds.compute
            ComputeService ComputeServiceContext ComputeServiceContextFactory]
           [org.jclouds.compute.domain
            Template TemplateBuilder ComputeMetadata NodeMetadata Hardware
            OsFamily Image]
           [org.jclouds.compute.options TemplateOptions]
           [org.jclouds.compute.predicates
            NodePredicates]
           [com.google.common.collect ImmutableSet]))

(try
 (use '[clojure.contrib.reflect :only [get-field]])
 (catch Exception e
   (use '[clojure.contrib.java-utils
          :only [wall-hack-field]
          :rename {wall-hack-field get-field}])))

(defn compute-service
  "Create a logged in context."
  ([#^String provider #^String provider-identity #^String provider-credential
    & options]
     (let [module-keys (set (keys module-lookup))
           ext-modules (filter #(module-keys %) options)
           opts (apply hash-map (filter #(not (module-keys %)) options))]
       (.. (ComputeServiceContextFactory.)
           (createContext
            provider provider-identity provider-credential
            (apply modules (concat ext-modules (opts :extensions)))
            (reduce #(do (.put %1 (name (first %2)) (second %2)) %1)
                    (Properties.) (dissoc opts :extensions)))
           (getComputeService)))))

(defn compute-context
  "Returns a compute context from a compute service."
  [compute]
  (.getContext compute))

(defn compute-service?
  [object]
  (instance? ComputeService object))

(defn compute-context?
  [object]
  (instance? ComputeServiceContext object))

(defn as-compute-service
  "Tries hard to produce a compute service from its input arguments"
  [& args]
  (cond
   (compute-service? (first args)) (first args)
   (compute-context? (first args)) (.getComputeService (first args))
   :else (apply compute-service args)))

(def *compute*)

(defmacro with-compute-service
  "Specify the default compute service"
  [[& compute-or-args] & body]
  `(binding [*compute* (as-compute-service ~@compute-or-args)]
     ~@body))

(defn locations
  "Retrieve the available compute locations for the compute context."
  ([] (locations *compute*))
  ([#^ComputeService compute]
     (seq (.listAssignableLocations compute))))

(defn nodes
  "Retrieve the existing nodes for the compute context."
  ([] (nodes *compute*))
  ([#^ComputeService compute]
    (seq (.listNodes compute))))

(defn nodes-with-details
  "Retrieve the existing nodes for the compute context."
  ([] (nodes-with-details *compute*))
  ([#^ComputeService compute]
    (seq (.listNodesDetailsMatching compute (NodePredicates/all)))))

(defn nodes-with-tag
  "list details of all the nodes with the given tag."
  ([tag] (nodes-with-tag tag *compute*))
  ([#^String tag #^ComputeService compute]
    (filter #(= (.getTag %) tag) (nodes-with-details compute))))

(defn images
  "Retrieve the available images for the compute context."
  ([] (images *compute*))
  ([#^ComputeService compute]
     (seq (.listImages compute))))

(defn hardware-profiles
  "Retrieve the available node hardware profiles for the compute context."
  ([] (hardware-profiles *compute*))
  ([#^ComputeService compute]
     (seq (.listHardwareProfiles compute))))

(defn default-template
  ([] (default-template *compute*))
  ([#^ComputeService compute]
     (.. compute (templateBuilder)
         (options
          (org.jclouds.compute.options.TemplateOptions$Builder/authorizePublicKey
           (slurp (str (. System getProperty "user.home") "/.ssh/id_rsa.pub"))))
         build)))

(defn run-nodes
  "Create the specified number of nodes using the default or specified
   template.

  ;; Simplest way to add 2 small linux nodes to the group webserver is to run
  (run-nodes \"webserver\" 2 compute)

  ;; which is the same as wrapping the run-nodes command with an implicit
  ;; compute service.
  ;; Note that this will actually add another 2 nodes to the set called
  ;; \"webserver\"

  (with-compute-service [compute]
    (run-nodes \"webserver\" 2 ))

  ;; which is the same as specifying the default template
  (with-compute-service [compute]
    (run-nodes \"webserver\" 2 (default-template)))

  ;; which, on gogrid, is the same as constructing the smallest centos template
  ;; that has no layered software
  (with-compute-service [compute]
    (run-nodes \"webserver\" 2
      (build-template
        service
        {:os-family :centos :smallest true
         :image-name-matches \".*w/ None.*\"})))"
  ([tag count]
     (run-nodes tag count (default-template *compute*) *compute*))
  ([tag count compute-or-template]
     (if (compute-service? compute-or-template)
       (run-nodes
        tag count (default-template compute-or-template) compute-or-template)
       (run-nodes tag count compute-or-template *compute*)))
  ([tag count template #^ComputeService compute]
     (seq
      (.runNodesWithTag compute tag count template))))

(defn run-node
  "Create a node using the default or specified template.

  ;; simplest way to add a small linux node to the group webserver is to run
  (run-node \"webserver\" compute)

  ;; which is the same as wrapping the run-node command with an implicit compute
  ;; service.
  ;; Note that this will actually add another node to the set called
  ;;  \"webserver\"
  (with-compute-service [compute]
    (run-node \"webserver\" ))"
  ([tag]
     (first (run-nodes tag 1 (default-template *compute*) *compute*)))
  ([tag compute-or-template]
     (if (compute-service? compute-or-template)
       (first
        (run-nodes
         tag 1 (default-template compute-or-template) compute-or-template))
       (first (run-nodes tag 1 compute-or-template *compute*))))
  ([tag template compute]
     (first (run-nodes tag 1 template compute))))

(defn #^NodeMetadata node-details
  "Retrieve the node metadata, given its id."
  ([id] (node-details id *compute*))
  ([id #^ComputeService compute]
     (.getNodeMetadata compute id)))

(defn suspend-nodes-with-tag
  "Reboot all the nodes with the given tag."
  ([tag] (suspend-nodes-with-tag tag *compute*))
  ([#^String tag #^ComputeService compute]
    (.suspendNodesMatching compute (NodePredicates/withTag tag))))

(defn suspend-node
  "Suspend a node, given its id."
  ([id] (suspend-node id *compute*))
  ([id #^ComputeService compute]
     (.suspendNode compute id)))

(defn resume-nodes-with-tag
  "Suspend all the nodes with the given tag."
  ([tag] (resume-nodes-with-tag tag *compute*))
  ([#^String tag #^ComputeService compute]
    (.resumeNodesMatching compute (NodePredicates/withTag tag))))

(defn resume-node
  "Resume a node, given its id."
  ([id] (resume-node id *compute*))
  ([id #^ComputeService compute]
     (.resumeNode compute id)))

(defn reboot-nodes-with-tag
  "Reboot all the nodes with the given tag."
  ([tag] (reboot-nodes-with-tag tag *compute*))
  ([#^String tag #^ComputeService compute]
    (.rebootNodesMatching compute (NodePredicates/withTag tag))))

(defn reboot-node
  "Reboot a node, given its id."
  ([id] (reboot-node id *compute*))
  ([id #^ComputeService compute]
     (.rebootNode compute id)))

(defn destroy-nodes-with-tag
  "Destroy all the nodes with the given tag."
  ([tag] (destroy-nodes-with-tag tag *compute*))
  ([#^String tag #^ComputeService compute]
     (.destroyNodesMatching compute (NodePredicates/withTag tag))))

(defn destroy-node
  "Destroy a node, given its id."
  ([id] (destroy-node id *compute*))
  ([id #^ComputeService compute]
     (.destroyNode compute id)))

(defmacro state-predicate [node state]
  `(= (.getState ~node)
      (. org.jclouds.compute.domain.NodeState ~state)))

(defn pending?
  "Predicate for the node being in transition"
  [#^NodeMetadata node]
  (state-predicate node PENDING))

(defn running?
  "Predicate for the node being available for requests."
  [#^NodeMetadata node]
  (state-predicate node RUNNING))

(defn terminated?
  "Predicate for the node being halted."
  [#^NodeMetadata node]
  (state-predicate node TERMINATED))

(defn suspended?
  "Predicate for the node being suspended."
  [#^NodeMetadata node]
  (state-predicate node SUSPENDED))

(defn error-state?
  "Predicate for the node being in an error state."
  [#^NodeMetadata node]
  (state-predicate node ERROR))

(defn unrecognized-state?
  "Predicate for the node being in an unrecognized state."
  [#^NodeMetadata node]
  (state-predicate node UNRECOGNIZED))

(defn public-ips
  "Returns the node's public ips"
  [#^NodeMetadata node]
  (.getPublicAddresses node))

(defn private-ips
  "Returns the node's private ips"
  [#^NodeMetadata node]
  (.getPrivateAddresses node))

(defn tag
  "Returns a the node's tag"
  [#^NodeMetadata node]
  (.getTag node))

(defn hostname
  "Returns the compute node's name"
  [#^ComputeMetadata node]
  (.getName node))

(defn location
  "Returns the compute node's location id"
  [#^ComputeMetadata node]
  (-?> node .getLocation .getId))

(defn id
  "Returns the compute node's id"
  [#^ComputeMetadata node]
  (.getId node))

(define-accessors Template image hardware location options)
(define-accessors Image version os-family os-description architecture)
(define-accessors Hardware processors ram volumes)
(define-accessors NodeMetadata "node" credentials hardware state tag)

(defn builder-options [builder]
  (or
   (get-field
    org.jclouds.compute.domain.internal.TemplateBuilderImpl :options builder)
   (TemplateOptions.)))

(defmacro option-option-fn-0arg [key]
  `(fn [builder#]
     (let [options# (builder-options builder#)]
       (~(symbol (str "." (camelize-mixed (name key)))) options#)
       (.options builder# options#))))

(defn- seq-to-array [args]
  (if (or (seq? args) (vector? args))
    (int-array args)
    args))

(defmacro option-option-fn-1arg [key]
  `(fn [builder# value#]
     (let [options# (builder-options builder#)]
       (~(symbol (str "." (camelize-mixed (name key))))
        options# (seq-to-array value#))
       (.options builder# options#))))

(def option-1arg-map
  (apply array-map
         (concat
          (make-option-map
           option-fn-1arg
           [:os-family :location-id :architecture :image-id :hardware-id
            :os-name-matches :os-version-matches :os-description-matches
            :os-64-bit :image-version-matches :image-name-matches
            :image-description-matches :min-cores :min-ram])
          (make-option-map
           option-option-fn-1arg
           [:run-script :install-private-key :authorize-public-key
            :inbound-ports]))))
(def option-0arg-map
     (apply hash-map
            (concat
             (make-option-map option-fn-0arg
                              [:smallest :fastest :biggest :any])
             (make-option-map option-option-fn-0arg
                              [:destroy-on-error]))))

(defn os-families []
  (. OsFamily values))

(def enum-map {:os-family (os-families)})

(defn translate-enum-value [kword value]
  (or (-> (filter #(= (name value) (str %)) (kword enum-map)) first)
      value))

(defn add-nullary-option [builder option value]
  (if-let [f (option-0arg-map option)]
    (if value
      (f builder)
      builder)))

(defn add-value-option [builder option value]
  (if-let [f (option-1arg-map option)]
    (f builder (translate-enum-value option value))))

;; TODO look at clojure-datalog
(defn build-template
  "Creates a template that can be used to run nodes.

The :os-family key expects a keyword version of OsFamily,
  eg. :os-family :ubuntu.

The :smallest, :fastest, :biggest, :any, and :destroy-on-error keys expect a
boolean value.

Options correspond to TemplateBuilder methods."
  [#^ComputeService compute
   {:keys [os-family location-id architecture image-id hardware-id
           os-name-matches os-version-matches os-description-matches
           os-64-bit image-version-matches image-name-matches
           image-description-matches min-cores min-ram
           run-script install-private-key authorize-public-key
           inbound-ports smallest fastest biggest any destroy-on-error]
    :as options}]
  (let [builder (.. compute (templateBuilder))]
    (doseq [[option value] options]
      (or
       (add-value-option builder option value)
       (add-nullary-option builder option value)
       (condition/raise
        :type :invalid-template-builder-option
        :message (format "Invalid template builder option : %s" option))))
    (.build builder)))
