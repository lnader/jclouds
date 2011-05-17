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
package org.jclouds.loadbalancer.config;

import org.jclouds.loadbalancer.strategy.DestroyLoadBalancerStrategy;
import org.jclouds.loadbalancer.strategy.GetLoadBalancerMetadataStrategy;
import org.jclouds.loadbalancer.strategy.ListLoadBalancersStrategy;
import org.jclouds.loadbalancer.strategy.LoadBalanceNodesStrategy;
import org.jclouds.loadbalancer.strategy.UpdateLoadBalancerStrategy;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * 
 * @author Adrian Cole
 * 
 */
public abstract class BindLoadBalancerStrategiesByClass extends AbstractModule {
   protected void configure() {
      bindLoadBalanceNodesStrategy(defineLoadBalanceNodesStrategy());
      bindListLoadBalancersStrategy(defineListLoadBalancersStrategy());
      bindGetLoadBalancerMetadataStrategy(defineGetLoadBalancerMetadataStrategy());
      bindDestroyLoadBalancerStrategy(defineDestroyLoadBalancerStrategy());
      bindUpdateLoadBalancerStrategy(defineUpdateLoadBalancerStrategy());
   }

   protected void bindLoadBalanceNodesStrategy(Class<? extends LoadBalanceNodesStrategy> clazz) {
      bind(LoadBalanceNodesStrategy.class).to(clazz).in(Scopes.SINGLETON);
   }

   protected void bindDestroyLoadBalancerStrategy(Class<? extends DestroyLoadBalancerStrategy> clazz) {
      bind(DestroyLoadBalancerStrategy.class).to(clazz).in(Scopes.SINGLETON);
   }

   protected void bindGetLoadBalancerMetadataStrategy(Class<? extends GetLoadBalancerMetadataStrategy> clazz) {
      bind(GetLoadBalancerMetadataStrategy.class).to(clazz).in(Scopes.SINGLETON);
   }

   protected void bindListLoadBalancersStrategy(Class<? extends ListLoadBalancersStrategy> clazz) {
      bind(ListLoadBalancersStrategy.class).to(clazz).in(Scopes.SINGLETON);
   }
   
   protected void bindUpdateLoadBalancerStrategy(Class<? extends UpdateLoadBalancerStrategy> clazz) {
       bind(UpdateLoadBalancerStrategy.class).to(clazz).in(Scopes.SINGLETON);
    }

   protected abstract Class<? extends LoadBalanceNodesStrategy> defineLoadBalanceNodesStrategy();

   protected abstract Class<? extends DestroyLoadBalancerStrategy> defineDestroyLoadBalancerStrategy();

   protected abstract Class<? extends GetLoadBalancerMetadataStrategy> defineGetLoadBalancerMetadataStrategy();

   protected abstract Class<? extends ListLoadBalancersStrategy> defineListLoadBalancersStrategy();
   
   protected abstract Class<? extends UpdateLoadBalancerStrategy> defineUpdateLoadBalancerStrategy();
}