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
package org.jclouds.elb.loadbalancer.strategy;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.getOnlyElement;
import static org.jclouds.aws.util.AWSUtils.parseHandle;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.elb.ELBClient;
import org.jclouds.elb.domain.LoadBalancer;
import org.jclouds.loadbalancer.domain.LoadBalancerMetadata;
import org.jclouds.loadbalancer.strategy.RemoveMembersFromLoadBalancerStrategy;

import com.google.common.base.Function;


@Singleton
public class ELBRemoveMembersFromLoadBalancerStrategy implements RemoveMembersFromLoadBalancerStrategy {

   private final ELBClient client;
   private final Function<LoadBalancer, LoadBalancerMetadata> converter;

   @Inject
   protected ELBRemoveMembersFromLoadBalancerStrategy(ELBClient client, Function<LoadBalancer, LoadBalancerMetadata> converter) {
      this.client = checkNotNull(client, "client");
      this.converter = checkNotNull(converter, "converter");
   }

   @Override
   public LoadBalancerMetadata removeMembersFromLoadBalancer(String id,
           Set<String> members) {
      String[] parts = parseHandle(id);
      String region = parts[0];
      String name = parts[1];
      client.deregisterInstancesWithLoadBalancerInRegion(region, name, members);
      try {
         return converter.apply(getOnlyElement(client.describeLoadBalancersInRegion(region, name)));
      } catch (NoSuchElementException e) {
         return null;
      }
   }

}