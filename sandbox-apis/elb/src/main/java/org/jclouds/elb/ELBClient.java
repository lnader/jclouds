/**
 *
 * Copyright (C) 2011 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package org.jclouds.elb;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.jclouds.concurrent.Timeout;
import org.jclouds.elb.domain.HealthCheck;
import org.jclouds.elb.domain.InstanceState;
import org.jclouds.elb.domain.Listener;
import org.jclouds.elb.domain.LoadBalancer;

import com.google.common.annotations.Beta;

/**
 * Provides access to EC2 Elastic Load Balancer via their REST API.
 * @see <a href="http://docs.amazonwebservices.com/ElasticLoadBalancing/latest/DeveloperGuide/
 * <p/>
 * 
 * @author Lili Nader
 */
@Beta
// see ELBAsyncClient
@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface ELBClient {
    

   /**
    * Creates a load balancer
    * @param region
    * @param name
    * @param listeners
    * @param availabilityZones
    * @return The DNS name for the LoadBalancer. 
    */
   @Beta
   String createLoadBalancerInRegion(@Nullable String region, String name, Set<Listener> listeners, List<String> availabilityZones);

   /**
    * Delete load balancer
    * @param region
    * @param name Name of the load balancer
    * @return
    */
   void deleteLoadBalancerInRegion(@Nullable String region, String name);

   /**
    *  Adds new instances to the LoadBalancer. Once the instance is registered, it starts receiving 
    *  traffic and requests from the LoadBalancer. Any instance that is not in any of the Availability 
    *  Zones registered for the LoadBalancer will be moved to the OutOfService state. It will move to 
    *  the InService state when the Availability Zone is added to the LoadBalancer.
    
    *  Note: In order for this call to be successful, the client must have created the LoadBalancer. 
    *  The client must provide the same account credentials as those that were used to create the LoadBalancer.
    *  
    *  Note: Completion of this API does not guarantee that operation has completed. Rather, it means 
    *  that the request has been registered and the changes will happen shortly. 
    * 
    * @param name Load Balancer name
    * @param instanceIds Set of instance Ids to register with load balancer
    * @return An updated list of instances for the LoadBalancer.
    * 
    */
   Set<String> registerInstancesWithLoadBalancerInRegion(@Nullable String region, String name, Set<String> instanceIds);

   /**
    *  Deregisters instances from the LoadBalancer. Once the instance is deregistered, it will stop 
    *  receiving traffic from the LoadBalancer. In order to successfully call this API, the same 
    *  account credentials as those used to create the LoadBalancer must be provided. 
    * 
    * @param name Load Balancer name
    * @param instanceIds Set of instance Ids to deregister with load balancer
    * @return An updated list of instances for the LoadBalancer.
    * 
    */
   Set<String> deregisterInstancesWithLoadBalancerInRegion(@Nullable String region, String name, Set<String> instanceIds);

   /**
    *  Returns detailed configuration information for the specified LoadBalancers. If no LoadBalancers 
    *  are specified, the operation returns configuration information for all LoadBalancers created by the caller.
    *  Note: The client must have created the specified input LoadBalancers in order to retrieve this information; 
    *  the client must provide the same account credentials as those that were used to create the LoadBalancer. 
    * 
    * @param region
    * @param loadbalancerNames
    *           names associated with the LoadBalancers at creation time.
    * @return A list of LoadBalancer
    */
   Set<? extends LoadBalancer> describeLoadBalancersInRegion(@Nullable String region, String... loadbalancerNames);

   /**
    *  Adds one or more EC2 Availability Zones to the LoadBalancer.  The LoadBalancer evenly distributes 
    *  requests across all its registered Availability Zones that contain instances. As a result, the 
    *  client must ensure that its LoadBalancer is appropriately scaled for each registered Availability Zone.
    *  Note: The new EC2 Availability Zones to be added must be in the same EC2 Region as the Availability 
    *  Zones for which the LoadBalancer was created. 
    *  
    * @param region
    * @param name
    * @param availabilityZones
    * @return Updated list of Availability Zones for the LoadBalancer.
    */
    Set<String> enableAvailabilityZonesForLoadBalancerInRegion(String region, String name,
        List<String> availabilityZones);
    
    /**
     * This API removes the specified EC2 Availability Zones from the set of configured Availability Zones 
     * for the LoadBalancer. Once an Availability Zone is removed, all the instances registered with the 
     * LoadBalancer that are in the removed Availability Zone go into the OutOfService state. Upon Availability 
     * Zone removal, the LoadBalancer attempts to equally balance the traffic among its remaining usable 
     * Availability Zones. Trying to remove an Availability Zone that was not associated with the LoadBalancer 
     * does nothing. 
     * 
     * There must be at least one Availability Zone registered with a LoadBalancer at all times. You cannot 
     * remove all the Availability Zones from a LoadBalancer.  
     * 
     * In order for this call to be successful, you must have created the LoadBalancer. In other words, 
     * in order to successfully call this API, you must provide the same account credentials as those 
     * that were used to create the LoadBalancer.
     * @param region
     * @param name
     * @param availabilityZones
     * @return
     */
    Set<String> disableAvailabilityZonesForLoadBalancerInRegion(String region, String name,
            List<String> availabilityZones);
    
    /**
     * Enables the client to define an application healthcheck for the instances. 
     * @param region
     * @param name
     * @param healthCheck
     * @return The updated healthcheck for the instances.
     */
    HealthCheck configureHealthCheckInRegion(String region, String name, HealthCheck healthCheck);
    
    /**
     *  Generates a stickiness policy with sticky session lifetimes that follow that of an application-generated 
     *  cookie. This policy can only be associated with HTTP listeners. 
     *  This policy is similar to the policy created by CreateLBCookieStickinessPolicy, except that the lifetime 
     *  of the special Elastic Load Balancing cookie follows the lifetime of the application-generated cookie 
     *  specified in the policy configuration. The load balancer only inserts a new stickiness cookie when the 
     *  application response includes a new application cookie.
     *  If the application cookie is explicitly removed or expires, the session stops being sticky until a new 
     *  application cookie is issued.
     *  Note: An application client must receive and send two cookies: the application-generated cookie and the 
     *  special Elastic Load Balancing cookie named AWSELB. This is the default behavior for many common web browsers. 
     * @param region
     * @param loadBalancerName
     * @param cookieName
     * @param policyName
     */
    void createAppCookieStickinessPolicyInRegion(String region, String loadBalancerName, String cookieName, String policyName);
    
    /**
     *  Generates a stickiness policy with sticky session lifetimes controlled by the lifetime of the browser 
     *  (user-agent) or a specified expiration period. This policy can be associated only with HTTP listeners. 
     *  
     *  When a load balancer implements this policy, the load balancer uses a special cookie to track the 
     *  backend server instance for each request. When the load balancer receives a request, it first checks 
     *  to see if this cookie is present in the request. If so, the load balancer sends the request to the 
     *  application server specified in the cookie. If not, the load balancer sends the request to a server 
     *  that is chosen based on the existing load balancing algorithm. 
     *  
     *  A cookie is inserted into the response for binding subsequent requests from the same user to that server. 
     *  The validity of the cookie is based on the cookie expiration time, which is specified in the policy 
     *  configuration. 
     *  
     * @param region
     * @param cookieExpirationPeriod
     * @param loadBalancerName
     * @param policyName
     */
    void createLBCookieStickinessPolicyInRegion(String region, Long cookieExpirationPeriod, String loadBalancerName, String policyName);

    /**
     * Creates one or more listeners on a LoadBalancer for the specified port. If a listener with the given port 
     * does not already exist, it will be created; otherwise, the properties of the new listener must match the 
     * properties of the existing listener. 
     * @param region
     * @param loadBalancerName
     * @param listeners
     */
    void createLoadBalancerListenersInRegion(String region, String loadBalancerName, Set<Listener> listeners);
   
    /**
     * Deletes listeners from the LoadBalancer for the specified port. 
     * @param region
     * @param loadBalancerName
     * @param ports
     */
    void deleteLoadBalancerListenersInRegion(String region,  String loadBalancerName, List<Integer> ports);
   
    /**
     * Deletes a policy from the LoadBalancer. The specified policy must not be enabled for any listeners. 
     * @param region
     * @param loadBalancerName
     * @param policyName
     */
    void deleteLoadBalancerPolicyInRegion(String region, String loadBalancerName,  String policyName);
    
    /**
     * A list containing health information for the specified instances. 
     * @param region
     * @param loadBalancerName
     * @param instanceIds
     * @return A list containing health information for the specified instances. 
     */
    Set<InstanceState> describeInstanceHealthInRegion(String region, String loadBalancerName, Set<String> instanceIds);
    
    /**
     * Sets the certificate that terminates the specified listener's SSL connections. The specified certificate 
     * replaces any prior certificate that was used on the same LoadBalancer and port. 
     * @param region
     * @param loadBalancerName
     * @param loadBalancerPort
     * @param sslCertificateId
     */
    void setLoadBalancerListenerSSLCertificateInRegion(String region, String loadBalancerName, Integer loadBalancerPort,
            String sslCertificateId);
    
    /**
     * Associates, updates, or disables a policy with a listener on the load balancer. Currently only zero (0) or one (1) 
     * policy can be associated with a listener. 
     * @param region
     * @param loadBalancerName
     * @param loadBalancerPort
     * @param policyNames
     */
    void setLoadBalancerPoliciesOfListenerInRegion(String region, String loadBalancerName, String loadBalancerPort, Set<String> policyNames);
    
}
