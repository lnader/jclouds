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

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.cloudloadbalancers.CloudLoadBalancersClient;
import org.jclouds.cloudloadbalancers.domain.LoadBalancer;
import org.jclouds.loadbalancer.domain.LoadBalancerMetadata;
import org.jclouds.loadbalancer.strategy.RemoveMembersFromLoadBalancerStrategy;

import com.google.common.base.Function;

/**
 * 
 * @author Adrian Cole
 */
@Singleton
public class CloudLoadBalancersRemoveMembersFromLoadBalancerStrategy implements RemoveMembersFromLoadBalancerStrategy {

   private final org.jclouds.cloudloadbalancers.CloudLoadBalancersClient client;
   private final Function<LoadBalancer, LoadBalancerMetadata> converter;

   @Inject
   protected CloudLoadBalancersRemoveMembersFromLoadBalancerStrategy(CloudLoadBalancersClient client,
            Function<LoadBalancer, LoadBalancerMetadata> converter) {
      this.client = checkNotNull(client, "client");
      this.converter = checkNotNull(converter, "converter");
   }

   @Override
   public LoadBalancerMetadata removeMembersFromLoadBalancer(String id,
           Set<String> members) {
      String[] parts = checkNotNull(id, "id").split("/");
      String region = parts[0];
      int lbId = Integer.parseInt(parts[1]);
      //TODO: remove members from load balancer
      return converter.apply(client.getLoadBalancerClient(region).getLoadBalancer(lbId));
   }

}