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

import static org.jclouds.aws.reference.FormParameters.ACTION;
import static org.jclouds.aws.reference.FormParameters.VERSION;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jclouds.aws.filters.FormSigner;
import org.jclouds.elb.binders.BindAvailabilityZonesToIndexedFormParams;
import org.jclouds.elb.binders.BindInstanceIdsToIndexedFormParams;
import org.jclouds.elb.binders.BindListenerPortsToIndexedFormParams;
import org.jclouds.elb.binders.BindListenersToIndexedFormParams;
import org.jclouds.elb.binders.BindLoadBalancerNamesToIndexedFormParams;
import org.jclouds.elb.binders.BindPolicyNamesToIndexedFormParams;
import org.jclouds.elb.domain.HealthCheck;
import org.jclouds.elb.domain.InstanceState;
import org.jclouds.elb.domain.Listener;
import org.jclouds.elb.domain.LoadBalancer;
import org.jclouds.elb.xml.AvailabilityZonesResponseHandler;
import org.jclouds.elb.xml.CreateLoadBalancerResponseHandler;
import org.jclouds.elb.xml.DescribeLoadBalancersResponseHandler;
import org.jclouds.elb.xml.InstancesResponseHandler;
import org.jclouds.location.functions.RegionToEndpointOrProviderIfNull;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.EndpointParam;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.FormParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.VirtualHost;
import org.jclouds.rest.annotations.XMLResponseParser;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;

import com.google.common.annotations.Beta;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides access to EC2 Elastic Load Balancer via REST API.
 * <p/>
 * 
 * @see <a href="http://docs.amazonwebservices.com/ElasticLoadBalancing/latest/APIReference/">ELB
 *      documentation</a>
 * @author Lili Nader
 */
@Beta
@RequestFilters(FormSigner.class)
@FormParams(keys = VERSION, values = ELBAsyncClient.VERSION)
@VirtualHost
public interface ELBAsyncClient {
   public static final String VERSION = "2010-07-01";

   // TODO: there are a lot of missing methods

   /**
    * @see ELBClient#createLoadBalancerInRegion
    */
   @POST
   @Path("/")
   @XMLResponseParser(CreateLoadBalancerResponseHandler.class)
   @FormParams(keys = ACTION, values = "CreateLoadBalancer")
   @Beta
   ListenableFuture<String> createLoadBalancerInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String name,
            @BinderParam(BindListenersToIndexedFormParams.class) Set<Listener> listeners,
            @BinderParam(BindAvailabilityZonesToIndexedFormParams.class) List<String> availabilityZones);

   /**
    * @see ELBClient#deleteLoadBalancerInRegion
    */
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "DeleteLoadBalancer")
   ListenableFuture<Void> deleteLoadBalancerInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String name);

   /**
    * @see ELBClient#registerInstancesWithLoadBalancerInRegion
    */
   @POST
   @Path("/")
   @XMLResponseParser(InstancesResponseHandler.class)
   @FormParams(keys = ACTION, values = "RegisterInstancesWithLoadBalancer")
   ListenableFuture<Set<String>> registerInstancesWithLoadBalancerInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String name,
            @BinderParam(BindInstanceIdsToIndexedFormParams.class) Set<String> instanceIds);

   /**
    * @see ELBClient#deregisterInstancesWithLoadBalancerInRegion
    */
   @POST
   @Path("/")
   @XMLResponseParser(InstancesResponseHandler.class)
   @FormParams(keys = ACTION, values = "DeregisterInstancesFromLoadBalancer")
   ListenableFuture<Set<String>> deregisterInstancesWithLoadBalancerInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String name,
            @BinderParam(BindInstanceIdsToIndexedFormParams.class) Set<String> instanceIds);

   /**
    * @see ELBClient#describeLoadBalancersInRegion
    */
   @POST
   @Path("/")
   @XMLResponseParser(DescribeLoadBalancersResponseHandler.class)
   @FormParams(keys = ACTION, values = "DescribeLoadBalancers")
   @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
   ListenableFuture<Set<? extends LoadBalancer>> describeLoadBalancersInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @BinderParam(BindLoadBalancerNamesToIndexedFormParams.class) String... loadbalancerNames);

   /**
    * @see ELBClient#enableAvailabilityZonesForLoadBalancerInRegion
    */
   
   @POST
   @Path("/")
   @XMLResponseParser(AvailabilityZonesResponseHandler.class)
   @FormParams(keys = ACTION, values = "EnableAvailabilityZonesForLoadBalancer")
   @Beta
   ListenableFuture<Set<String>> enableAvailabilityZonesForLoadBalancerInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String name,
            @BinderParam(BindAvailabilityZonesToIndexedFormParams.class) List<String> availabilityZones);
   
   @POST
   @Path("/")
   @XMLResponseParser(AvailabilityZonesResponseHandler.class)
   @FormParams(keys = ACTION, values = "DisableAvailabilityZonesForLoadBalancer")
   @Beta
   ListenableFuture<Set<String>> disableAvailabilityZonesForLoadBalancerInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String name,
            @BinderParam(BindAvailabilityZonesToIndexedFormParams.class) List<String> availabilityZones);
   
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "ConfigureHealthCheck")
   @Beta
   ListenableFuture<HealthCheck> configureHealthCheckInRegion(
           @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
           @FormParam("LoadBalancerName") String name, 
           @FormParam("HealthCheck") HealthCheck healcheck);
   
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "CreateAppCookieStickinessPolicy")
   @Beta
   ListenableFuture<Void> createAppCookieStickinessPolicyInRegion(
           @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
           @FormParam("LoadBalancerName") String loadBalancerName, 
           @FormParam("CookieName") String cookieName,
           @FormParam("PolicyName") String policyName);
   
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "CreateLBCookieStickinessPolicy")
   @Beta
   ListenableFuture<Void> createLBCookieStickinessPolicyInRegion(
           @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
           @Nullable @FormParam("CookieExpirationPeriod") Long cookieExpirationPeriod,
           @FormParam("LoadBalancerName") String loadBalancerName, 
           @FormParam("PolicyName") String policyName);
   
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "CreateLoadBalancerListeners")
   ListenableFuture<Void> createLoadBalancerListenersInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String loadBalancerName,
            @BinderParam(BindListenersToIndexedFormParams.class) Set<Listener> listeners);
   
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "DeleteLoadBalancerListeners")
   ListenableFuture<Void> deleteLoadBalancerListenersInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String loadBalancerName,
            @BinderParam(BindListenerPortsToIndexedFormParams.class) List<Integer> ports);
   
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "DeleteLoadBalancerPolicy")
   @Beta
   ListenableFuture<Void> deleteLoadBalancerPolicyInRegion(
           @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
           @FormParam("LoadBalancerName") String loadBalancerName, 
           @FormParam("PolicyName") String policyName);
   
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "DescribeInstanceHealth")
   ListenableFuture<InstanceState> describeInstanceHealthInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String loadBalancerName,
            @BinderParam(BindInstanceIdsToIndexedFormParams.class) Set<String> instanceIds);
   
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "SetLoadBalancerListenerSSLCertificate")
   @Beta
   ListenableFuture<Void> setLoadBalancerListenerSSLCertificateInRegion(
           @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
           @FormParam("LoadBalancerName") String loadBalancerName, 
           @FormParam("LoadBalancerPort") Integer loadBalancerPort,
           @FormParam("SSLCertificateId") String sslCertificateId);
   
   
   @POST
   @Path("/")
   @FormParams(keys = ACTION, values = "SetLoadBalancerPoliciesOfListener")
   @Beta
   ListenableFuture<Void> setLoadBalancerPoliciesOfListenerInRegion(
            @EndpointParam(parser = RegionToEndpointOrProviderIfNull.class) @Nullable String region,
            @FormParam("LoadBalancerName") String loadBalancerName,
            @FormParam("LoadBalancerPort") String loadBalancerPort,
            @BinderParam(BindPolicyNamesToIndexedFormParams.class) Set<String> policyNames);
}
