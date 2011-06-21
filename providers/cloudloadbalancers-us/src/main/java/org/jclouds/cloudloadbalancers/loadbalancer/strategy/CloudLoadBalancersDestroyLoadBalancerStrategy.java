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
package org.jclouds.cloudloadbalancers.loadbalancer.strategy;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.cloudloadbalancers.CloudLoadBalancersClient;
import org.jclouds.loadbalancer.domain.LoadBalancerMetadata;
import org.jclouds.loadbalancer.reference.LoadBalancerConstants;
import org.jclouds.loadbalancer.strategy.DestroyLoadBalancerStrategy;
import org.jclouds.loadbalancer.strategy.GetLoadBalancerMetadataStrategy;
import org.jclouds.logging.Logger;

/**
 * 
 * @author Adrian Cole
 */
@Singleton
public class CloudLoadBalancersDestroyLoadBalancerStrategy implements DestroyLoadBalancerStrategy {
   @Resource
   @Named(LoadBalancerConstants.LOADBALANCER_LOGGER)
   protected Logger logger = Logger.NULL;

   private final CloudLoadBalancersClient client;
   private final GetLoadBalancerMetadataStrategy getLoadBalancer;

   @Inject
   protected CloudLoadBalancersDestroyLoadBalancerStrategy(CloudLoadBalancersClient client,
           GetLoadBalancerMetadataStrategy getLoadBalancer) {
      this.client = checkNotNull(client, "client");
      this.getLoadBalancer = checkNotNull(getLoadBalancer, "getLoadBalancer");
   }

   @Override
   public LoadBalancerMetadata destroyLoadBalancer(String id) {
      String[] parts = checkNotNull(id, "id").split("/");
      String region = parts[0];
      int lbId = Integer.parseInt(parts[1]);
      client.getLoadBalancerClient(region).removeLoadBalancer(lbId);
      return getLoadBalancer.getLoadBalancer(id);
   }
}
